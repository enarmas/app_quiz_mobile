
package com.hnsam.quiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hnsam.quiz.R;
import com.hnsam.quiz.Constant;
import com.hnsam.quiz.helper.Session;
import com.hnsam.quiz.helper.Utils;
import com.hnsam.quiz.model.Level;
import com.hnsam.quiz.model.Review;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class LevelActivity extends AppCompatActivity {
    public Toolbar toolbar;
    LevelListAdapter adapter;
    public static String fromQue;
    private static int levelNo = 1;
    List<Level> levelList;
    RecyclerView recyclerView;
    public TextView emptyMsg;
    public ProgressBar progressBar;
    public static ArrayList<Review> reviews = new ArrayList<>();
    public RelativeLayout layout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Utils.transparentStatusAndNavigation(LevelActivity.this);
        layout = findViewById(R.id.layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.select_level);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        AdView mAdView = findViewById(R.id.banner_AdView);
        mAdView.loadAd(new AdRequest.Builder().build());
        invalidateOptionsMenu();
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        emptyMsg = findViewById(R.id.empty_msg);

        recyclerView.setLayoutManager(new LinearLayoutManager(LevelActivity.this));
        levelNo = MainActivity.dbHelper.GetLevelById(Constant.CATE_ID, Constant.SUB_CAT_ID);

        fromQue = getIntent().getStringExtra("fromQue");
        levelList = new ArrayList<>();

        for (int i = 0; i < Constant.TotalLevel; i++) {
            Level level = new Level();
            level.setLevelNo(levelNo);
            level.setLevel("" + (i + 1));
            level.setQuestion(getString(R.string.que) + Constant.MAX_QUESTION_PER_LEVEL);
            levelList.add(level);
        }
        if (levelList.size() == 0) {
            emptyMsg.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            getData();
        }
    }


    private void getData() {
        if (Utils.isNetworkAvailable(LevelActivity.this)) {
            adapter = new LevelListAdapter(LevelActivity.this, levelList);
            recyclerView.setAdapter(adapter);
        } else {
            setSnackBar();
        }
    }

    public void setSnackBar() {
        Snackbar snackbar = Snackbar
                .make(layout, getString(R.string.msg_no_internet), Snackbar.LENGTH_INDEFINITE)
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

    public class LevelListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public Activity activity;
        private List<Level> levelList;

        public LevelListAdapter(Activity activity, List<Level> levelList) {
            this.levelList = levelList;
            this.activity = activity;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lock_item, parent, false);
            return new LevelViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            LevelViewHolder viewHolder = (LevelViewHolder) holder;

            Level level = levelList.get(position);
            viewHolder.levelNo.setText(getString(R.string.level_txt) + level.getLevel());
            viewHolder.tvQuestion.setText(level.getQuestion());

            if (level.getLevelNo() >= position + 1) {
                viewHolder.lock.setImageResource(R.drawable.unlock);
            } else {
                viewHolder.lock.setImageResource(R.drawable.lock);
            }

            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Session.getSoundEnableDisable(activity)) {
                        Utils.backSoundonclick(activity);
                    }
                    if (Session.getVibration(activity)) {
                        Utils.vibrate(activity, Utils.VIBRATION_DURATION);
                    }
                    Utils.RequestlevelNo = position + 1;
                    if (levelNo >= position + 1) {
                        Intent intent = new Intent(LevelActivity.this, PlayActivity.class);
                        intent.putExtra("fromQue", fromQue);
                        startActivity(intent);
                    } else {
                        Toast.makeText(activity, getString(R.string.level_locked), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return levelList.size();
        }

        public class LevelViewHolder extends RecyclerView.ViewHolder {

            ImageView lock;
            CardView cardView;
            TextView levelNo, tvQuestion;

            public LevelViewHolder(View itemView) {
                super(itemView);
                lock = (ImageView) itemView.findViewById(R.id.lock);
                levelNo = (TextView) itemView.findViewById(R.id.level_no);
                tvQuestion = (TextView) itemView.findViewById(R.id.question_no);
                cardView = (CardView) itemView.findViewById(R.id.cardView);
            }
        }
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
                Utils.CheckVibrateOrSound(LevelActivity.this);
                Intent playQuiz = new Intent(LevelActivity.this, SettingActivity.class);
                startActivity(playQuiz);
                overridePendingTransition(R.anim.open_next, R.anim.close_next);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

