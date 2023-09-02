package com.example.a1190075_1190245_courseproject.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tweeter";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE `user` (" +
                    "id TEXT PRIMARY KEY," +
                    "firstName TEXT," +
                    "lastName TEXT," +
                    "nickName TEXT," +
                    "email TEXT," +
                    "passwords TEXT," +
                    "preference TEXT" +
                    ")";

    private static final String CREATE_NOTE_TABLE = "CREATE TABLE `note` ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " title TEXT," +
            " content TEXT," +
            " creationDate TEXT," +
            " isFavorite INTEGER)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists and create tables again
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS note");
        onCreate(db);
    }
}
