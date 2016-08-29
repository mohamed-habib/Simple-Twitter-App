package com.example.android.simpletwitterapp.apis;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.simpletwitterapp.database.DatabaseOperations;
import com.example.android.simpletwitterapp.database.UserTable;
import com.example.android.simpletwitterapp.model.User;
import com.example.android.simpletwitterapp.model.Users;
import com.example.android.simpletwitterapp.utils.AppConstClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class CallGetFollowersAPI extends AsyncTask<String, Void, Users> {
    public static final String TAG = "Authenticating";

    @Override
    protected Users doInBackground(String... params) {
        OAuthConsumer consumer = new DefaultOAuthConsumer(AppConstClass.CONSUMER_KEY, AppConstClass.CONSUMER_SECRET);
        consumer.setTokenWithSecret(params[0], params[1]);
        Users users = new Users();
        String cursor = params[2];
        String count = "20";
        try {
            URL url_getFollowersIds = new URL(AppConstClass.URL_GET_FOLLOWERS_IDS + "?cursor=" + cursor + "&count=" + count);
            HttpURLConnection request = (HttpURLConnection) url_getFollowersIds.openConnection();
            consumer.sign(request);
            request.connect();

            InputStream in = new BufferedInputStream(request.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            JSONObject responseJsonObject = new JSONObject(reader.readLine());
            JSONArray jsonArrayIds = responseJsonObject.getJSONArray("ids");
            List<User> userList = new ArrayList<>();
            String nextCursor = responseJsonObject.getString("next_cursor");
            String parameters = "?user_id=" + jsonArrayIds.get(0).toString();
            for (int i = 1; i < jsonArrayIds.length(); i++) {
                String id = jsonArrayIds.get(i).toString();
                parameters += "," + jsonArrayIds.get(i).toString();
                Log.d(TAG, "ID: " + id);
            }

            URL url_getFollowersLookup = new URL(AppConstClass.URL_GET_FOLLOWERS_LOOKUP + parameters);
            request = (HttpURLConnection) url_getFollowersLookup.openConnection();
            consumer.sign(request);
            request.connect();

            in = new BufferedInputStream(request.getInputStream());
            reader = new BufferedReader(new InputStreamReader(in));
            JSONArray jsonArrayUsers = new JSONArray(reader.readLine());
            for (int i = 0; i < jsonArrayUsers.length(); i++) {
                Log.d(TAG, "Obj: " + jsonArrayUsers.get(i).toString());
                JSONObject userJsonObject = jsonArrayUsers.getJSONObject(i);
                userList.add(new User(userJsonObject.getString("profile_image_url").replace("normal", "bigger"), userJsonObject.getString("screen_name"), userJsonObject.getString("id"),
                        userJsonObject.getString("profile_background_image_url"), userJsonObject.getString("profile_background_color"), userJsonObject.getString("description"), userJsonObject.getString("name")));
                Log.d(TAG, "IMG: " + userJsonObject.getString("profile_image_url"));

                UserTable userTable = new UserTable(userJsonObject.getString("profile_image_url").replace("normal", "bigger"), userJsonObject.getString("screen_name"), userJsonObject.getString("id"),
                        userJsonObject.getString("profile_background_image_url"), userJsonObject.getString("profile_background_color"), userJsonObject.getString("description"), userJsonObject.getString("name"));
                userTable.save();
            }

            users.nextCursor = nextCursor;
            users.userList = userList;
            Log.d(TAG, "Response: " + jsonArrayUsers.toString());
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
        } finally {
            DatabaseOperations databaseOperations = new DatabaseOperations();
            for (UserTable userTable : databaseOperations.getAllUsers())
                users.userList.add(userTable.getUser());

        }

        return users;
    }

}

