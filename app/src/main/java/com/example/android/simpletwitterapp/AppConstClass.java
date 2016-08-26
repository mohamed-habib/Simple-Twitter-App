package com.example.android.simpletwitterapp;

/**
 * Created by Mohamed Habib on 8/25/2016.
 */
public class AppConstClass {
    public static final String URL_ROOT_TWITTER_API = "https://api.twitter.com";
    public static final String URL_GET_FOLLOWERS_IDS = URL_ROOT_TWITTER_API + "/1.1/followers/ids.json";
    public static final String URL_AUTHENTICATION = URL_ROOT_TWITTER_API + "/oauth2/token";


    public static final String SHARED_PREFERENCES_FILE_NAME = "SimpleTwitterApp";
    public static final String LOGGED_IN = "LoggedIn";
    public static final String USER_TOKEN = "UserToken";
    public static final String USER_NAME = "UserName";
    public static final String USER_ID = "UserId";
}
