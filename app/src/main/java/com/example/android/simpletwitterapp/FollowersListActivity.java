package com.example.android.simpletwitterapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class FollowersListActivity extends AppCompatActivity {
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_followers_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_followers_recycler_view);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

    }
}
