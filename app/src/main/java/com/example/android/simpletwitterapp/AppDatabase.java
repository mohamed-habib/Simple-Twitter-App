package com.example.android.simpletwitterapp;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Mohamed Habib on 8/27/2016.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "AppDatabase"; // we will add the .db extension

    public static final int VERSION = 1;

}
