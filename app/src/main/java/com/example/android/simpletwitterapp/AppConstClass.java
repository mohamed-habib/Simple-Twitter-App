package com.example.android.simpletwitterapp;

/**
 * Created by Mohamed Habib on 8/25/2016.
 */
public class AppConstClass {
    public static final String URL_ROOT_TWITTER_API = "https://api.twitter.com/1.1/";
    public static final String URL_GET_FOLLOWERS_IDS = URL_ROOT_TWITTER_API + "followers/ids.json";
    public static final String URL_GET_FOLLOWERS_LOOKUP = URL_ROOT_TWITTER_API + "users/lookup.json";
    public static final String URL_GET_FOLLOWERS_TWEETS = URL_ROOT_TWITTER_API + "statuses/user_timeline.json";


    public static final String SHARED_PREFERENCES_FILE_NAME = "SimpleTwitterApp";
    public static final String LOGGED_IN = "LoggedIn";
    public static final String USER_TOKEN = "UserToken";
    public static final String USER_SECRET = "UserSecret";
    public static final String USER_NAME = "UserName";
    public static final String USER_ID = "UserId";
    public static final String SELECTED_USER = "SelectedUser";
    public static final String NEXT_FOLLOWERS_CURSOR = "NextFollowersCursor";
}
