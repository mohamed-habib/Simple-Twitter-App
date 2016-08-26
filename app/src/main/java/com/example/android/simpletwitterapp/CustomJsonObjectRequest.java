package com.example.android.simpletwitterapp;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Mohamed Habib on 8/25/2016.
 */
public class CustomJsonObjectRequest extends JsonObjectRequest {
    public CustomJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

//    @Override
//    public Map getHeaders() throws AuthFailureError {
//        Long tsLong = System.currentTimeMillis() / 1000;
//        String oauth_timestamp = tsLong.toString();
//
//        Map headers = new HashMap();
//        headers.put("oauth_consumer_key", AppConstClass.TWITTER_KEY);
//        headers.put("oauth_nonce", AppConstClass.OAUTH_NONCE);
//        headers.put("oauth_signature", AppConstClass.OAUTH_SIGNATURE);
//        headers.put("oauth_signature_method", AppConstClass.OAUTH_SIGNATURE_METHOD);
//        headers.put("oauth_timestamp", oauth_timestamp);
//        headers.put("oauth_token", AppConstClass.OAUTH_TOKEN);
//        headers.put("oauth_version", AppConstClass.OAUTH_VERSION);
//        return headers;
//    }

}

