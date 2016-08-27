package com.example.android.simpletwitterapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Created by Mohamed Habib on 8/28/2016.
 */
class CallGetTweetsAPI extends AsyncTask<String, Void, List<String>> {
    public static final String TAG = "Get Tweets";

    @Override
    protected List<String> doInBackground(String... params) {

        OAuthConsumer consumer = new DefaultOAuthConsumer(AppConstClass.TWITTER_KEY, AppConstClass.TWITTER_SECRET);
        consumer.setTokenWithSecret(params[0], params[1]);
        List<String> tweets = new ArrayList<>();
        String userId = params[2];
        String count = "10";

        try {
            URL url_getFollowersIds = new URL(AppConstClass.URL_GET_FOLLOWERS_TWEETS + "?user_id=" + userId + "&count=" + count);
            HttpURLConnection request = (HttpURLConnection) url_getFollowersIds.openConnection();
            consumer.sign(request);
            request.connect();

            InputStream in = new BufferedInputStream(request.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            JSONArray tweetsJsonArray = new JSONArray(reader.readLine());
            for (int i = 0; i < tweetsJsonArray.length(); i++) {
                tweets.add(tweetsJsonArray.getJSONObject(i).getString("text"));
            }
            Log.d(TAG, "Response: " + tweetsJsonArray.toString());
        } catch (IOException e) {
            Log.d(TAG, "IOException: " + e.toString());
        } catch (OAuthExpectationFailedException e) {
            Log.d(TAG, "OAuthExpectationFailedException: " + e.toString());
        } catch (OAuthMessageSignerException e) {
            Log.d(TAG, "OAuthMessageSignerException: " + e.toString());
        } catch (OAuthCommunicationException e) {
            Log.d(TAG, "OAuthCommunicationException: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweets;
    }

}

