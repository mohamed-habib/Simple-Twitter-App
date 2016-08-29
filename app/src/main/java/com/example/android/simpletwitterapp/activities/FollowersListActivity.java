package com.example.android.simpletwitterapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.simpletwitterapp.database.DatabaseOperations;
import com.example.android.simpletwitterapp.R;
import com.example.android.simpletwitterapp.apis.CallGetFollowersAPI;
import com.example.android.simpletwitterapp.database.UserTable;
import com.example.android.simpletwitterapp.model.User;
import com.example.android.simpletwitterapp.model.Users;
import com.example.android.simpletwitterapp.utils.AppConstClass;
import com.example.android.simpletwitterapp.utils.OnLoadMoreListener;
import com.example.android.simpletwitterapp.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FollowersListActivity extends AppCompatActivity {
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    SharedPreferences sharedPreferences;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_followers_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_followers_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sharedPreferences = getSharedPreferences(AppConstClass.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);

        final List<User> userList = getFollowerList("-1");
        final FollowersRVAdapter followersRVAdapter = new FollowersRVAdapter(userList, FollowersListActivity.this);
        mRecyclerView.setAdapter(followersRVAdapter);
        handler = new Handler();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<User> userList = getFollowerList("-1");
                FollowersRVAdapter followersRVAdapter = new FollowersRVAdapter(userList, FollowersListActivity.this);
                mRecyclerView.setAdapter(followersRVAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        followersRVAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                userList.add(null);
                followersRVAdapter.notifyItemInserted(userList.size() - 1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                        userList.remove(userList.size() - 1);
                        followersRVAdapter.notifyItemRemoved(userList.size());
                        //add items one by one
                        userList.addAll(getFollowerList(sharedPreferences.getString(AppConstClass.NEXT_FOLLOWERS_CURSOR, "-1")));
                        followersRVAdapter.notifyItemInserted(userList.size());
                        followersRVAdapter.setLoaded();
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                }, 2000);

            }
        });


    }

    @NonNull
    private List<User> getFollowerList(String cursor) {
        String token = sharedPreferences.getString(AppConstClass.USER_TOKEN, "");
        String tokenSecret = sharedPreferences.getString(AppConstClass.USER_SECRET, "");
        String[] params = new String[]{token, tokenSecret, cursor};
        List<User> userList = new ArrayList<>();
        DatabaseOperations databaseOperations = new DatabaseOperations();
        if (Utilities.isNetworkAvailable(FollowersListActivity.this)) {
            try {
                Users users = new CallGetFollowersAPI().execute(params).get();
                userList = users.userList;
                sharedPreferences.edit().putString(AppConstClass.NEXT_FOLLOWERS_CURSOR, users.nextCursor).apply();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            for (UserTable userTable : databaseOperations.getAllUsers())
                userList.add(userTable.getUser());
            if (userList.size() < 1)
                Snackbar.make(mRecyclerView, "Check Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFollowerList("-1");
                    }
                }).show();
        }
        return userList;
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

    class FollowersRVAdapter extends RecyclerView.Adapter {

        List<User> userList;
        Context context;

        private final int VIEW_ITEM = 1;
        private final int VIEW_PROG = 0;

        // The minimum amount of items to have below your current scroll position
        // before loading more.
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        private boolean loading;
        private OnLoadMoreListener onLoadMoreListener;


        FollowersRVAdapter(List<User> userList, Context context) {
            this.userList = userList;
            this.context = context;
            if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {

                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();


                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager
                                .findLastVisibleItemPosition();
                        if (!loading
                                && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            // End has been reached
                            // Do something
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                            loading = true;
                        }
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            return userList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            if (viewType == VIEW_ITEM) {
                vh = new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.followers_list_item, parent, false));

            } else {
                View v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.progress_item, parent, false);

                vh = new ProgressViewHolder(v);
            }
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof UserViewHolder) {

                final User user = userList.get(position);
                ((UserViewHolder) holder).bioTV.setText(user.description);
                ((UserViewHolder) holder).handleTV.setText(user.screen_name);
                ((UserViewHolder) holder).fullNameTV.setText(user.fullName);
                ((UserViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FollowersListActivity.this, FollowerInformationActivity.class);
                        intent.putExtra(AppConstClass.SELECTED_USER, user);
                        startActivity(intent);
                    }
                });
                Picasso.with(context).load(user.profile_img).into(((UserViewHolder) holder).profileIV);
            } else {
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            }
        }

        public void setLoaded() {
            loading = false;
        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }

        @Override
        public int getItemCount() {
            if (userList != null)
                return userList.size();
            else return 0;
        }

        class UserViewHolder extends RecyclerView.ViewHolder {
            ImageView profileIV;
            TextView fullNameTV;
            TextView handleTV;
            TextView bioTV;
            View itemView;

            public UserViewHolder(View itemView) {
                super(itemView);
                profileIV = (ImageView) itemView.findViewById(R.id.follower_profile_image_view);
                fullNameTV = (TextView) itemView.findViewById(R.id.follower_full_name_text_view);
                handleTV = (TextView) itemView.findViewById(R.id.follower_handle_text_view);
                bioTV = (TextView) itemView.findViewById(R.id.follower_bio_text_view);
                this.itemView = itemView;
            }
        }

        class ProgressViewHolder extends RecyclerView.ViewHolder {
            public ProgressBar progressBar;

            public ProgressViewHolder(View v) {
                super(v);
                progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
            }
        }
    }

}
