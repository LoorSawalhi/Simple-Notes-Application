package com.example.a1190075_1190245_courseproject.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1190075_1190245_courseproject.enums.Preference;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes";
    private static final int DATABASE_VERSION = 4;

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
                    "creationDate Date," +
                    "isFavourite INTEGER DEFAULT 0," +
                    "tagId TEXT DEFAULT '0'," +
                    "FOREIGN KEY(userId) REFERENCES Users(id)" +
                    ")";

    private static final String CREATE_TAG_TABLE =
            "CREATE TABLE `tag` (" +
                    "id TEXT PRIMARY KEY," +
                    "label TEXT," +
                    "userId TEXT," +
                    "FOREIGN KEY(userId) REFERENCES Users(id)" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_NOTE_TABLE);
        db.execSQL(CREATE_TAG_TABLE);

        db.execSQL("INSERT INTO `user` (id, firstName, lastName, nickName, email, passwords, preference) VALUES ('1', 'Ahmad', 'Abbas', 'ahmad', '1', '1', \"" + Preference.CREATIONDATE.name() +"\")");
        db.execSQL("INSERT INTO `user` (id, firstName, lastName, nickName, email, passwords, preference) VALUES ('2', 'Loor', 'Sawalhi', 'loor', 'loor@example.com', 'password', \"" + Preference.CREATIONDATE.name() +"\")");

        db.execSQL("INSERT INTO `tag` (id, label, userId) VALUES ('0', 'All', '1')");
        db.execSQL("INSERT INTO `tag` (id, label, userId) VALUES ('1', 'All', '2')");
        db.execSQL("INSERT INTO `tag` (id, label, userId) VALUES ('2', 'Work', '1')");
        db.execSQL("INSERT INTO `tag` (id, label, userId) VALUES ('3', 'Personal', '1')");
        db.execSQL("INSERT INTO `tag` (id, label, userId) VALUES ('4', 'Family', '2')");
        db.execSQL("INSERT INTO `tag` (id, label, userId) VALUES ('5', 'Work', '2')");
        db.execSQL("INSERT INTO `tag` (id, label, userId) VALUES ('6', 'Hobbies', '1')");
        db.execSQL("INSERT INTO `tag` (id, label, userId) VALUES ('7', 'Travel', '2')");

        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('1', '1', 'Android Components', 'Learn about Activity, Service, and BroadcastReceiver.', '2023-09-10', 1, '2')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('2', '1', 'Kotlin Libraries', 'Explore Coroutines and Flow.', '2023-09-09', 0, '2')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('3', '2', 'Android Networking', 'Retrofit and OkHttp.', '2023-01-03', 1, '1')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('4', '2', 'Design Patterns', 'MVC, MVP, and MVVM in Android.', '2023-01-04', 0, '4')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('5', '1', 'Shared Note on RxJava', 'Learning RxJava for Android.', '2023-01-05', 1, '3')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('6', '2', 'Shared Note on Room', 'Exploring Room database.', '2023-01-05', 1, '5')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('7', '1', 'Guitar Lessons', 'Learn E major and G major.', '2023-01-06', 0, '6')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('8', '2', 'Travel Itinerary', 'Trip to Paris.', '2023-01-07', 1, '7')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('9', '1', 'Photography Tips', 'Understanding ISO, aperture, and shutter speed.', '2023-01-08', 0, '6')");
        db.execSQL("INSERT INTO `note` (id, userId, title, content, creationDate, isFavourite, tagId) VALUES ('10', '2', 'Cooking Recipes', 'Homemade pizza and pasta.', '2023-01-09', 1, '7')");

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
