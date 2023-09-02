package com.example.a1190075_1190245_courseproject.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dto.UserDto;
import com.example.a1190075_1190245_courseproject.enums.Preference;
import com.example.a1190075_1190245_courseproject.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {
    private SQLiteDatabase database;
    private final DatabaseHelper databaseHelper;

    public UserDaoImpl(DatabaseHelper databaseHelper) {
        database = databaseHelper.getWritableDatabase();
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
            UserDto user = cursorToUser(cursor);
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

        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE email = ?", new String[]{id});

        close();

        if (cursor != null) {
            cursor.moveToFirst();
            UserDto user = cursorToUser(cursor);
            cursor.close();
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void insert(UserDto user) {
        openWrite();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("nickName", user.getNickName());
        values.put("email", user.getEmail());
        values.put("passwords", user.getPasswords());
        values.put("preference", user.getPreference().toString());

        database.insert("User", null, values);

        close();
    }

    @Override
    public void update(UserDto user) {
        openWrite();
        ContentValues values = new ContentValues();
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("nickName", user.getNickName());
        values.put("email", user.getEmail());
        values.put("passwords", user.getPasswords());
        values.put("preference", user.getPreference().toString());

        database.update("User", values, "id=?", new String[]{user.getId()});

        close();
    }

    public UserDto loginUser(String email, String password) {
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE email = ? AND password = ?", new String[]{email, password});
        if (cursor.moveToFirst()) {
            UserDto user = cursorToUser(cursor);

            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }
    @Override
    public void delete(String id) {
        openWrite();
        database.delete("User", "id=?", new String[]{id});
        close();
    }

    @SuppressLint("Range")
    private UserDto cursorToUser(Cursor cursor) {
        UserDto user = new UserDto();
        user.setId(cursor.getString(cursor.getColumnIndex("id")));
        user.setFirstName(cursor.getString(cursor.getColumnIndex("firstName")));
        user.setLastName(cursor.getString(cursor.getColumnIndex("lastName")));
        user.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
        user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        user.setPasswords(cursor.getString(cursor.getColumnIndex("passwords")));

        if(cursor.getString(cursor.getColumnIndex("preference")).equals(Preference.ALPHABETICALLY.toString())) {
            user.setPreference(Preference.ALPHABETICALLY);
        } else {
            user.setPreference(Preference.CREATION_DATE);
        }
        return user;
    }
}
