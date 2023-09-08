package com.example.a1190075_1190245_courseproject.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.UserDto;
import com.example.a1190075_1190245_courseproject.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {
    private SQLiteDatabase database;
    private final DatabaseHelper databaseHelper;

    public UserDaoImpl(DatabaseHelper databaseHelper) {
        this.databaseHelper  = databaseHelper;
    }

    public void openWrite() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void openRead() throws SQLException {
        database = databaseHelper.getReadableDatabase();
    }
    public void close() {
        databaseHelper.close();
    }


    @Override
    public List<UserDto> list() {
        openRead();
        List<UserDto> users = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM user", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserDto user = UserDto.cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();

        close();
        return users;
    }

    @Override
    public UserDto findById(String id) {
        openRead();

        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE id = ?", new String[]{id});



        if (cursor != null) {
            cursor.moveToFirst();
            UserDto user = UserDto.cursorToUser(cursor);
            cursor.close();
            return user;
        }
        close();
        return null;
    }

    @Override
    public boolean insert(UserDto user) {
        openWrite();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("nickName", user.getNickName());
        values.put("email", user.getEmail());
        values.put("passwords", user.getPassword());
        values.put("preference", user.getPreference().name());

        try {
            database.insertOrThrow("`user`", null, values);  // Table name should match what's defined in SQL
            close();
            return true;
        } catch (SQLiteConstraintException e) {
            Log.e("DatabaseHelper", "Error inserting new user: ", e);
            close();
            return false;
        }
    }

    @Override
    public int update(UserDto user) {
        openWrite();
        ContentValues values = new ContentValues();
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("nickName", user.getNickName());
        values.put("email", user.getEmail());
        values.put("passwords", user.getPassword());
        values.put("preference", user.getPreference().toString());

        int rows = database.update("User", values, "id=?", new String[]{user.getId()});

        close();
        return rows;
    }



    @Override
    public int delete(String id) {
        openWrite();
        int rows = database.delete("User", "id=?", new String[]{id});
        close();
        return rows;
    }

    @Override
    public List<NoteDto> favouriteNotes(String userId) {
        openRead();
        List<NoteDto> notes = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM note WHERE userId = ? AND isFavourite = 1", new String[]{userId});

        if (cursor.moveToFirst()) {
            do {
                NoteDto note = NoteDto.cursorToNote(cursor);
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        return notes;
    }


    @Override
    public UserDto loginUser(String email, String password) {
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE email = ? AND password = ?", new String[]{email, password});
        if (cursor.moveToFirst()) {
            UserDto user = UserDto.cursorToUser(cursor);

            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }
}
