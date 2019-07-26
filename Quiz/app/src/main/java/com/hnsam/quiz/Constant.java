package com.hnsam.quiz;

public class Constant {

    //////////////////////// POST API & PARAMETER  //////////////////////////

    public static String QUIZ_URL = "http://quiz18.website/quiz/api-v2.php";
    /*FireBase function URL*/
    public static String FIREBASE_URL = "https://us-central1-quiz-d1765.cloudfunctions.net/";

    /////// PARAMETERS  ///////
    public static String accessKey = "access_key";
    public static String accessKeyValue = "6808";
    public static String name = "name";
    public static String email = "email";
    public static String mobile = "mobile";
    public static String type = "type";
    public static String fcmId = "fcm_id";
    public static String userId = "user_id";
    public static String PROFILE = "profile";
    public static String userSignUp = "user_signup";
    public static String status = "status";
    public static String ipAddress = "ip_address";
    public static String getCategories = "get_categories";
    public static String getRandomQuestion = "get_random_questions";
    public static String getQuestionByLevel = "get_questions_by_level";
    public static String getSubCategory = "get_subcategory_by_maincategory";
    public static String categoryId = "main_id";
    public static String getQuestion = "get_questions_by_subcategory";
    public static String getUserScore = "get_monthly_users_score";
    public static String subCategoryId = "subcategory";
    public static String reportQuestion = "report_question";
    public static String questionId = "question_id";
    public static String messageReport = "message";
    public static String getQuestionByCategory = "get_questions_by_category";
    public static String getQuestionForRobot = "get_random_questions_for_computer";
    public static String category = "category";
    public static String Level = "level";
    public static String getPrivacy = "privacy_policy_settings";
    public static String getTerms = "get_terms_conditions_settings";
    public static String terms = "terms";
    public static String privacy = "privacy_policy";
    public static String upload_profile_image = "upload_profile_image";
    public static String image = "image";
    public static String updateFcmId = "update_fcm_id";
    public static String updateProfile = "update_profile";
    public static String getMontlyLeaderboard = "get_monthly_leaderboard";
    public static String setMonthlyLeaderboard = "set_monthly_leaderboard";
    public static String NO_OF_CATE = "no_of";
    public static String GET_USER_BY_ID = "get_user_by_id";

    public static String DATE = "date";
    public static String RANK = "rank";
    public static String SCORE = "score";
    public static String ERROR = "error";
    public static String DATA = "data";
    public static String ID = "id";
    public static String CATEGORY_NAME = "category_name";
    public static String IMAGE = "image";
    public static String MAX_LEVEL = "maxlevel";
    public static String MAIN_CATE_ID = "maincat_id";
    public static String SUB_CATE_NAME = "subcategory_name";

    public static String QUESTION = "question";
    public static String OPTION_A = "optiona";
    public static String OPTION_B = "optionb";
    public static String OPTION_C = "optionc";
    public static String OPTION_D = "optiond";
    public static String LEVEL = "level";
    public static String NOTE = "note";
    public static String USER_ID_1 = "user_id_1";
    public static String USER_ID_2 = "user_id_2";
    public static String FCM_ID_1 = "fcm_id_1";
    public static String FCM_ID_2 = "fcm_id_2";
    public static String GAME_ROOM_KEY = "match_id";
    public static String DE_ACTIVE="0";

    /*-----------fireBase database column names for battle---------*/
    public static String AVAILABILITY = "availability";
    public static String STATUS = "status";
    public static String DB_GAME_ROOM = "game_room";
    public static String DB_USER = "user";
    public static String USER_NAME = "name";
    public static String USER_ID = "user_id";
    public static String FCM_ID = "fcm_id";
    public static String PROFILE_PIC = "profile_Pic";
    public static String ONLINE_STATUS = "online_status";
    public static String QUE_NO = "que_no";
    public static String RIGHT = "right";
    public static String WRONG = "wrong";
    public static String SEL_ANS = "sel_ans";
    public static String DESTROY_GAME_KEY = "destroy_match";
    public static String GameRoomKey = "";

    public static int MAX_QUESTION_PER_LEVEL = 10; // max question per level
    public static long LeftTime;

    public static int TotalLevel;
    public static int CATE_ID=1;
    public static int SUB_CAT_ID;

    public static String PROGRESS_COLOR = "#606899"; // change progress color in play area while choose audience pole
    public static String PROGRESS_BG_COLOR = "#d8d8d8";
    public static String AUD_PROGRESS_COLOR = "#393d5a";
    public static String AUD_PROGRESS_BG_COLOR = "#d8d8d8";
    public static String STATUS_BAR_COLOR = "#393D5A";

    public static int PROGRESS_TEXT_SIZE = 13;
    public static int PROGRESS_STROKE_WIDTH = 6;
    public static int RESULT_PROGRESS_STROKE_WIDTH = 10;
    public static int RESULT_PROGRESS_TEXT_SIZE = 20;
    public static int AUD_PROGRESS_STROKE_WIDTH = 5;
    public static int AUD_PROGRESS_TEXT_SIZE = 10;


    public static final String PREF_TEXTSIZE = "fontSizePref";
    //max text size
    public static final String TEXTSIZE_MAX = "30";
    //minimum text size
    public static final String TEXTSIZE_MIN = "16";
    /// you can increase or decrease time
    public static int CIRCULAR_MAX_PROGRESS = 25; // max progress of circular progress
    public static int TIME_PER_QUESTION = 25000;  //here we set 25 second foe each question
    public static int COUNT_DOWN_TIMER = 1000; //here we set 1 second

    public static final String PLAY_STORE_URL = "http://play.google.com/store/apps/details?id=";
    public static final String MORE_APP_URL = "https://play.google.com/store/apps/developer?id=%s&hl=en";

    //////------------give coin to user , when level completed----------//////

    public static int correctAnswer = 3;  //count level complete when user give >30 percent correct answer
    public static int giveOneCoin = 1;  //give  coin when user give 30 to 40 percent correct answer
    public static int giveTwoCoins = 2; //give  coins when user give 40 to 50 percent correct answer
    public static int giveThreeCoins = 3; //give  coin when user give 50 to 60 percent correct answer
    public static int giveFourCoins = 4;  //give  coin when user give > 60  percent correct answer
    public static String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghjiklmnopqrstuvwxyz";

}
