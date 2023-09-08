package com.example.a1190075_1190245_courseproject.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1190075_1190245_courseproject.enums.Preference;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes";
    private static final int DATABASE_VERSION = 3;

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE `user` (" +
                    "id TEXT PRIMARY KEY," +
                    "firstName TEXT," +
                    "lastName TEXT," +
                    "nickName TEXT," +
                    "email TEXT UNIQUE," +
                    "passwords TEXT," +
                    "preference TEXT" +
                    ")";

    private static final String CREATE_NOTE_TABLE =
            "CREATE TABLE `note` ( " +
                    "id TEXT PRIMARY KEY," +
                    "userId TEXT," +
                    "title TEXT," +
                    "content TEXT," +
                    "creationDate TEXT," +
                    "isFavourite INTEGER DEFAULT 0," +
                    "tagId TEXT DEFAULT '0'," +
                    "FOREIGN KEY(userId) REFERENCES Users(id)" +
                    ")";

    private static final String CREATE_TAG_TABLE =
            "CREATE TABLE `tag` (" +
                    "id TEXT PRIMARY KEY," +
                    "label TEXT UNIQUE" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_NOTE_TABLE);
        db.execSQL(CREATE_TAG_TABLE);

        db.execSQL("INSERT INTO `user` (id, firstName, lastName, nickName, email, passwords, preference) VALUES ('1', 'John', 'Doe', 'johnny', '1', '1', \"" + Preference.CREATIONDATE.name() +"\")");
        db.execSQL("INSERT INTO `user` (id, firstName, lastName, nickName, email, passwords, preference) VALUES ('2', 'Jane', 'Doe', 'jenny', 'jane@example.com', 'password', \"" + Preference.CREATIONDATE.name() +"\")");

        db.execSQL("INSERT INTO `tag` (id, label) VALUES ('1', 'Work')");
        db.execSQL("INSERT INTO `tag` (id, label) VALUES ('2', 'Personal')");
        db.execSQL("INSERT INTO `tag` (id, label) VALUES ('3', 'Family')");

        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('1', '1', 'Meeting notes', 'Discuss project plans.', '2021-01-01', 1, '1')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('2', '1', 'Grocery list', 'Milk, Bread', '2021-01-02', 0, '2')");

        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('3', '2', 'Family Reunion', 'Plan for the family reunion next month.', '2021-01-03', 1, '3')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('4', '2', 'Read List', 'Books to read this year.', '2021-01-04', 0, '2')");

        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('5', '1', 'Shared Note', 'This is a shared note.', '2021-01-05', 1, '2')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('6', '2', 'Shared Note', 'This is a shared note.', '2021-01-05', 1, '2')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // V1
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS note");

        // V2
        db.execSQL("DROP TABLE IF EXISTS tag");
        db.execSQL("DROP TABLE IF EXISTS note_tag");
        onCreate(db);
    }
}
