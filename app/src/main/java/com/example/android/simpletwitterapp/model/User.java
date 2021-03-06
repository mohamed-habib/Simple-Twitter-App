package com.example.android.simpletwitterapp.model;

import java.io.Serializable;

/**
 * Created by Mohamed Habib on 8/28/2016.
 */
public class User implements Serializable {
    public String screen_name;
    public String user_id;
    public String profile_img;
    public String profile_background;
    public String profile_background_color;
    public String description;
    public String fullName;

    public User(String profile_img, String screen_name, String user_id, String profile_background, String profile_background_color, String description, String fullName) {
        this.profile_img = profile_img;
        this.screen_name = screen_name;
        this.user_id = user_id;
        this.profile_background = profile_background;
        this.profile_background_color = profile_background_color;
        this.description = description;
        this.fullName = fullName;
    }
}
