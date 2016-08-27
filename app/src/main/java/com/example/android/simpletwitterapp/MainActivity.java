package com.example.android.simpletwitterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    TwitterLoginButton twitterLoginButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(AppConstClass.CONSUMER_KEY, AppConstClass.CONSUMER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        sharedPreferences = getSharedPreferences(AppConstClass.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(AppConstClass.LOGGED_IN, false)) {
            startActivity(new Intent(this, FollowersListActivity.class));
        }
        setContentView(R.layout.activity_main);
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        twitterLoginButton.setCallback(new LoginHandler(MainActivity.this));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

}
