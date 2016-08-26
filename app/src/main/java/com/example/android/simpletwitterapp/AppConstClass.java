package com.example.android.simpletwitterapp;

/**
 * Created by Mohamed Habib on 8/25/2016.
 */
public class AppConstClass {
    public static final String URL_ROOT_TWITTER_API = "https://api.twitter.com";
    public static final String URL_GET_FOLLOWERS_IDS = URL_ROOT_TWITTER_API + "/1.1/followers/ids.json";
    public static final String URL_AUTHENTICATION = URL_ROOT_TWITTER_API + "/oauth2/token";
    public static final String TWITTER_SECRET = "jZAzKmM3phvYuGZFzWfq68DGOSKfYfg8zsLwiR6b8UtRk1TcI4";
    public static final String OAUTH_NONCE = "9f82b8e4b99125fc84cab38b75a54695";
    public static final String OAUTH_SIGNATURE = "Iyj59zueWsKoRShghwuzz8EuX1k%3D";
    public static final String OAUTH_SIGNATURE_METHOD = "HMAC-SHA1%3D";
    public static final String OAUTH_TOKEN = "463802417-g0bBLATf3IKNtMLpq81LMh3refSf4E7qAScWxsH1";
    public static final String OAUTH_VERSION = "1.0";
    public static final String TWITTER_KEY = "rfyL46H2cWTdYrk6JjdeeXISz";


    public static final String SHARED_PREFERENCES_FILE_NAME = "SimpleTwitterApp";
    public static final String LOGGED_IN = "LoggedIn";
    public static final String USER_TOKEN = "UserToken";
    public static final String USER_NAME = "UserName";
    public static final String USER_ID = "UserId";
}
