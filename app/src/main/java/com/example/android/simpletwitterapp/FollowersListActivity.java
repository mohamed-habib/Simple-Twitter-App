package com.example.android.simpletwitterapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FollowersListActivity extends AppCompatActivity {
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_followers_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_followers_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sharedPreferences = getSharedPreferences(AppConstClass.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);

        List<Follower> followers = getFollowerList();
        FollowersRVAdapter followersRVAdapter = new FollowersRVAdapter(followers, FollowersListActivity.this);
        mRecyclerView.setAdapter(followersRVAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<Follower> followers = getFollowerList();
                FollowersRVAdapter followersRVAdapter = new FollowersRVAdapter(followers, FollowersListActivity.this);
                mRecyclerView.setAdapter(followersRVAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @NonNull
    private List<Follower> getFollowerList() {
        List<Follower> followers = new ArrayList<>();
        followers.add(new Follower("", "Mohamed Habib", "Mohamed_Habib12", "Computer Science graduate, Android Developer and Loves Video Games"));
        followers.add(new Follower("", "Mohamed Habib", "Mohamed_Habib12", ""));
        followers.add(new Follower("", "Mohamed Habib", "Mohamed_Habib12", "Computer Science graduate, Android DeveloperComputer Science graduate, Android DeveloperComputer Science graduate, Android Developer"));
        followers.add(new Follower("", "Mohamed Habib", "Mohamed_Habib12", "Computer Science graduate, Android Developer"));
        return followers;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            sharedPreferences.edit().clear().apply();
            finish();
        }
        return true;
    }

    class FollowersRVAdapter extends RecyclerView.Adapter<FollowersRVAdapter.ViewHolder> {

        List<Follower> followerList;
        Context context;

        FollowersRVAdapter(List<Follower> followerList, Context context) {
            this.followerList = followerList;
            this.context = context;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.followers_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Follower follower = followerList.get(position);
//            holder.profileIV.
            holder.bioTV.setText(follower.bio);
            holder.handleTV.setText(follower.handle);
            holder.fullNameTV.setText(follower.fullName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FollowersListActivity.this, followerInformation.class));
                }
            });

        }

        @Override
        public int getItemCount() {
            return followerList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView profileIV;
            TextView fullNameTV;
            TextView handleTV;
            TextView bioTV;
            View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                profileIV = (ImageView) itemView.findViewById(R.id.follower_profile_image_view);
                fullNameTV = (TextView) itemView.findViewById(R.id.follower_full_name_text_view);
                handleTV = (TextView) itemView.findViewById(R.id.follower_handle_text_view);
                bioTV = (TextView) itemView.findViewById(R.id.follower_bio_text_view);
                this.itemView = itemView;
            }
        }
    }

}
