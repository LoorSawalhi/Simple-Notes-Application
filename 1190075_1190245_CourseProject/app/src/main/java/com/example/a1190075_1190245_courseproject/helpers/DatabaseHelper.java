package com.example.a1190075_1190245_courseproject.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1190075_1190245_courseproject.enums.Preference;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "twitter";
    private static final int DATABASE_VERSION = 2;

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
                    " title TEXT," +
                    " content TEXT," +
                    " creationDate TEXT," +
                    "FOREIGN KEY(userId) REFERENCES Users(id)" +
                    ")";

    private static final String CREATE_FAVOURITE_TABLE =
            "CREATE TABLE `favourite` (" +
                    "    id TEXT PRIMARY KEY," +
                    "    userId TEXT," +
                    "    noteId TEXT," +
                    "    FOREIGN KEY(userId) REFERENCES Users(id)," +
                    "    FOREIGN KEY(noteId) REFERENCES Notes(id)" +
                    ")";

    private static final String CREATE_TAG_TABLE =
            "CREATE TABLE `tag` (" +
                    "id TEXT PRIMARY KEY," +
                    "label TEXT UNIQUE" +
                    ")";

    private static final String CREATE_NOTE_TAG_TABLE =
            "CREATE TABLE `note_tag` (" +
                    "id TEXT PRIMARY KEY," +
                    "noteId TEXT," +
                    "tagId TEXT," +
                    "userId TEXT," +
                    "FOREIGN KEY(noteId) REFERENCES note(id)," +
                    "FOREIGN KEY(tagId) REFERENCES tag(id)," +
                    "FOREIGN KEY(userId) REFERENCES user(id)" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_NOTE_TABLE);
        db.execSQL(CREATE_FAVOURITE_TABLE);
        db.execSQL(CREATE_TAG_TABLE);
        db.execSQL(CREATE_NOTE_TAG_TABLE);

        // Users
        db.execSQL("INSERT INTO user (id, firstName, lastName, nickName, email, passwords, preference) VALUES " +
                "('1', 'Ahmad', 'Abbas', 'AhmadAbbas', '123', '123'," + Preference.CREATION_DATE.ordinal() + ")," +
                "('2', 'Jane', 'Doe', 'jane', 'jane@example.com', 'password123', " + Preference.ALPHABETICALLY.ordinal() + ")");

        // Notes
        db.execSQL("INSERT INTO note (id, userId, title, content, creationDate) VALUES " +
                "('note1', '1', 'Note 1', 'Content 1', '01-01-2023')," +
                "('note2', '2', 'Note 2', 'Content 2', '02-01-2023')");

        // Favourites
        db.execSQL("INSERT INTO favourite (id, userId, noteId) VALUES " +
                "('fav1', '1', 'note1')," +
                "('fav2', '2', 'note2')");

        // Tags
        db.execSQL("INSERT INTO tag (id, label) VALUES " +
                "('tag1', 'Tag 1')," +
                "('tag2', 'Tag 2')");

        // Note Tags
        db.execSQL("INSERT INTO note_tag (id, noteId, tagId, userId) VALUES " +
                "('notetag1', 'note1', 'tag1', '1')," +
                "('notetag2', 'note2', 'tag2', '2')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // V1
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS note");
        db.execSQL("DROP TABLE IF EXISTS favourite");

        // V2
        db.execSQL("DROP TABLE IF EXISTS tag");
        db.execSQL("DROP TABLE IF EXISTS note_tag");

        onCreate(db);
    }
}
