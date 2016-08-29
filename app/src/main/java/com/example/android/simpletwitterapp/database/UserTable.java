package com.example.android.simpletwitterapp.database;

import com.example.android.simpletwitterapp.model.User;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Mohamed Habib on 8/27/2016.
 */
@Table(database = AppDatabase.class)
public class UserTable extends BaseModel {
    @PrimaryKey(autoincrement = true)
    long id;
    @Column
    String twitterId;
    @Column
    String screen_name;
    @Column
    String profile_img;
    @Column
    String profile_background;
    @Column
    String profile_background_color;
    @Column
    String description;
    @Column
    String fullName;

    public UserTable() {
    }

    public UserTable(String profile_img, String screen_name, String user_id, String profile_background, String profile_background_color, String description, String fullName) {
        this.profile_img = profile_img;
        this.screen_name = screen_name;
        this.twitterId = user_id;
        this.profile_background = profile_background;
        this.profile_background_color = profile_background_color;
        this.description = description;
        this.fullName = fullName;
    }

    public User getUser() {
        return new User(profile_img, screen_name, twitterId, profile_background, profile_background_color, description, fullName);
    }


}
