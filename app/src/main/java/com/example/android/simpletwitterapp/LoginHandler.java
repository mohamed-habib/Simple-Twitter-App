package com.example.android.simpletwitterapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.concurrent.ExecutionException;

/**
 * Created by Mohamed Habib on 8/25/2016.
 */
public class LoginHandler extends Callback<TwitterSession> {

    public static final String TAG = "Authenticating";
    Context context;
    SharedPreferences sharedPreferences;

    public LoginHandler(Context context) {
        this.context = context;
    }

    @Override
    public void success(Result<TwitterSession> result) {
        String userName = result.data.getUserName();
        String token = result.data.getAuthToken().token;
        String tokenSecret = result.data.getAuthToken().secret;
        long id = result.data.getId();

        String output = "Status: " +
                "Your login was successful " + userName +
                "\nAuth Token Received: " + token +
                "\nId " + id;
        Log.d(TAG, output);
        sharedPreferences = context.getApplicationContext().getSharedPreferences(AppConstClass.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(AppConstClass.LOGGED_IN, true).apply();
        sharedPreferences.edit().putString(AppConstClass.USER_NAME, userName).apply();
        sharedPreferences.edit().putString(AppConstClass.USER_TOKEN, token).apply();
        sharedPreferences.edit().putString(AppConstClass.USER_SECRET, tokenSecret).apply();
        sharedPreferences.edit().putLong(AppConstClass.USER_ID, id).apply();

        String[] params = new String[]{token, tokenSecret, "-1"};
        try {
            Users users = new CallGetFollowersAPI().execute(params).get();
            sharedPreferences.edit().putString(AppConstClass.NEXT_FOLLOWERS_CURSOR, users.nextCursor);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        context.startActivity(new Intent(context, FollowersListActivity.class));
    }

    @Override
    public void failure(TwitterException exception) {
        Log.d("UserData", "Login Failed");
    }


}
