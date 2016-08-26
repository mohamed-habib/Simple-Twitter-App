package com.example.android.simpletwitterapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Mohamed Habib on 8/25/2016.
 */
public class LoginHandler extends Callback<TwitterSession> {

    Context context;
    SharedPreferences sharedPreferences;

    public LoginHandler(Context context) {
        this.context = context;
    }

    @Override
    public void success(Result<TwitterSession> result) {
        String userName = result.data.getUserName();
        String token = result.data.getAuthToken().token;
        long id = result.data.getId();

        String output = "Status: " +
                "Your login was successful " + userName +
                "\nAuth Token Received: " + token +
                "\nId " + id;
        Log.d("UserData", output);

        sharedPreferences = context.getApplicationContext().getSharedPreferences(AppConstClass.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(AppConstClass.LOGGED_IN, true).apply();
        sharedPreferences.edit().putString(AppConstClass.USER_NAME, userName).apply();
        sharedPreferences.edit().putString(AppConstClass.USER_TOKEN, token).apply();
        sharedPreferences.edit().putLong(AppConstClass.USER_ID, id).apply();

        context.startActivity(new Intent(context, FollowersListActivity.class));
    }

    @Override
    public void failure(TwitterException exception) {
        Log.d("UserData", "Login Failed");
    }


}
