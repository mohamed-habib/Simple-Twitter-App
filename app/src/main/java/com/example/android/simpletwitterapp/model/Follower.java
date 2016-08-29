package com.example.android.simpletwitterapp.model;

/**
 * Created by Mohamed Habib on 8/26/2016.
 */
public class Follower {

    String profileImg;
    String fullName;
    String handle;
    String bio;

    public Follower(String profileImg, String fullName, String handle, String bio) {
        this.profileImg = profileImg;
        this.fullName = fullName;
        this.handle = handle;
        this.bio = bio;
    }
}
