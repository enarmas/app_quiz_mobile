package com.hnsam.quiz.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.hnsam.quiz.R;
import com.hnsam.quiz.helper.AppController;
import com.hnsam.quiz.helper.CircleImageView;
import com.hnsam.quiz.Constant;
import com.hnsam.quiz.helper.Utils;
import com.hnsam.quiz.model.SubCategory;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubcategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public ProgressBar progressBar;
    public ArrayList<SubCategory> subCateList;
    public AdView mAdView;
    public TextView txtBlankList;
    public RelativeLayout layout;

    public SwipeRefreshLayout swipeRefreshLayout;
    public Snackbar snackbar;
    public Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        layout = findViewById(R.id.layout);
        Utils.transparentStatusAndNavigation(SubcategoryActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.select_subcategory));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mAdView = findViewById(R.id.banner_AdView);
        mAdView.loadAd(new AdRequest.Builder().build());

        txtBlankList = findViewById(R.id.txtblanklist);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SubcategoryActivity.this));
        txtBlankList.setText(getString(R.string.no_category));

        subCateList = new ArrayList<>();
        getData();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                subCateList.clear();
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        if (Utils.isNetworkAvailable(SubcategoryActivity.this)) {
            getSubCategoryFromJson();

        } else {
            setSnackBar();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getSubCategoryFromJson() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.QUIZ_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean(Constant.ERROR);

                            if (!error) {
                                JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    SubCategory subCate = new SubCategory();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    subCate.setId(object.getString(Constant.ID));
                                    subCate.setCategoryId(object.getString(Constant.MAIN_CATE_ID));
                                    subCate.setName(object.getString(Constant.SUB_CATE_NAME));
                                    subCate.setImage(object.getString(Constant.IMAGE));
                                    subCate.setStatus(object.getString(Constant.STATUS));
                                    subCate.setMaxLevel(object.getString(Constant.MAX_LEVEL));
                                    subCateList.add(subCate);


                                }
                                if (subCateList.size() == 0) {
                                    txtBlankList.setVisibility(View.VISIBLE);
                                    txtBlankList.setText(getString(R.string.no_sub_category));
                                }
                                SubCategoryAdapter adapter = new SubCategoryAdapter(SubcategoryActivity.this, subCateList);
                                recyclerView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }/*else{
                                if(Constant.TotalLevel!=0){
                                    Intent intent = new Intent(SubcategoryActivity.this, LevelActivity.class);
                                    intent.putExtra("fromQue", "subCate");
                                    startActivity(intent);
                                    ((SubcategoryActivity)getApplicationContext()).finish();
                                }
                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.GONE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Constant.accessKey, Constant.accessKeyValue);
                params.put(Constant.getSubCategory, "1");
                params.put(Constant.categoryId, "" + Constant.CATE_ID);
                System.out.println("params  " + params.toString());
                return params;
            }
        };
        AppController.getInstance().getRequestQueue().getCache().clear();
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    public void setSnackBar() {
        snackbar = Snackbar
                .make(findViewById(android.R.id.content), getString(R.string.msg_no_internet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getData();
                    }
                });

        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ItemRowHolder> {
        private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        private ArrayList<SubCategory> dataList;
        private Context mContext;

        public SubCategoryAdapter(Context context, ArrayList<SubCategory> dataList) {
            this.dataList = dataList;
            this.mContext = context;
        }

        @Override
        public SubCategoryAdapter.ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false);
            return new ItemRowHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull SubCategoryAdapter.ItemRowHolder holder, final int position) {
            txtBlankList.setVisibility(View.GONE);
            final SubCategory subCate = dataList.get(position);
            holder.text.setText(subCate.getName());
            holder.image.setDefaultImageResId(R.drawable.ic_launcher);
            holder.image.setImageUrl(subCate.getImage(), imageLoader);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.SUB_CAT_ID = Integer.parseInt(subCate.getId());
                    if (subCate.getMaxLevel().isEmpty()) {
                        Constant.TotalLevel = 0;
                    } else {
                        Constant.TotalLevel = Integer.parseInt(subCate.getMaxLevel());
                    }
                    Intent intent = new Intent(mContext, LevelActivity.class);
                    intent.putExtra("fromQue", "subCate");
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return (null != dataList ? dataList.size() : 0);
        }

        public class ItemRowHolder extends RecyclerView.ViewHolder {
            public CircleImageView image;
            public TextView text;
            RelativeLayout relativeLayout;

            public ItemRowHolder(View itemView) {
                super(itemView);
                image = (CircleImageView) itemView.findViewById(R.id.imgcategory);
                text = (TextView) itemView.findViewById(R.id.item_title);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.cat_layout);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (snackbar != null)
            snackbar.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.bookmark).setVisible(false);
        menu.findItem(R.id.report).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.setting:
                Utils.CheckVibrateOrSound(SubcategoryActivity.this);
                Intent playQuiz = new Intent(SubcategoryActivity.this, SettingActivity.class);
                startActivity(playQuiz);
                overridePendingTransition(R.anim.open_next, R.anim.close_next);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}