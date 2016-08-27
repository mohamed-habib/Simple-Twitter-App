package com.example.android.simpletwitterapp;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by Mohamed Habib on 8/27/2016.
 */
public class DatabaseOperations {


    public List<UserTable> getAllUsers() {
        return SQLite.select().from(UserTable.class).queryList();
    }
}
