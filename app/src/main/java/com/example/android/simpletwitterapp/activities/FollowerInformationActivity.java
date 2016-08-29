package com.example.android.simpletwitterapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.simpletwitterapp.R;
import com.example.android.simpletwitterapp.apis.CallGetTweetsAPI;
import com.example.android.simpletwitterapp.model.User;
import com.example.android.simpletwitterapp.utils.AppConstClass;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowerInformationActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    RecyclerView recyclerView;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    CircleImageView profileImgCircleImageView;
    TextView userNameTV;
    TextView screenNameTV;
    ImageView backgroundIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_infromation);

        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        profileImgCircleImageView = (CircleImageView) findViewById(R.id.activity_follower_information_user_profile_circle_img_view);
        mTitle = (TextView) findViewById(R.id.activity_follower_information_toolbar_title);
        screenNameTV = (TextView) findViewById(R.id.activity_follower_information_handle_tv);
        userNameTV = (TextView) findViewById(R.id.activity_follower_information_user_name_tv);
        backgroundIV = (ImageView) findViewById(R.id.main_imageview_follower_background);

        recyclerView = (RecyclerView) findViewById(R.id.tweets_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        User user = (User) getIntent().getSerializableExtra(AppConstClass.SELECTED_USER);

        screenNameTV.setText(user.screen_name);
        userNameTV.setText(user.fullName);
        mTitle.setText(user.fullName + "'s Tweets");
        Picasso.with(this).load(user.profile_img).into(profileImgCircleImageView);
        if (!user.profile_background.equals("null")) {
            Picasso.with(this).load(user.profile_background).into(profileImgCircleImageView);
        } else {
            //todo: set the color
        }

        List<String> tweets = getTweets(user.user_id);
        recyclerView.setAdapter(new TweetsRVAdapter(tweets, this));


    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @NonNull
    private List<String> getTweets(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstClass.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(AppConstClass.USER_TOKEN, "");
        String tokenSecret = sharedPreferences.getString(AppConstClass.USER_SECRET, "");
        String[] params = new String[]{token, tokenSecret, userId};
        List<String> tweets = null;
        try {
            tweets = new CallGetTweetsAPI().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return tweets;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    class TweetsRVAdapter extends RecyclerView.Adapter<TweetsRVAdapter.TweetViewHolder> {
        List<String> tweetList;
        Context context;

        TweetsRVAdapter(List<String> tweets, Context context) {
            this.tweetList = tweets;
            this.context = context;
        }

        @Override
        public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TweetViewHolder(LayoutInflater.from(context).inflate(R.layout.tweets_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(TweetViewHolder holder, int position) {
            holder.tweet.setText(tweetList.get(position));
        }

        @Override
        public int getItemCount() {
            return tweetList.size();
        }

        class TweetViewHolder extends RecyclerView.ViewHolder {
            TextView tweet;

            public TweetViewHolder(View itemView) {
                super(itemView);
                tweet = (TextView) itemView.findViewById(R.id.tweet_tv);
            }
        }
    }

}
