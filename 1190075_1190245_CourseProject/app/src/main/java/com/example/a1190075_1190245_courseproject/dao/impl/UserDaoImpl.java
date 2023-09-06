package com.example.a1190075_1190245_courseproject.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dto.FavouriteDto;
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
    public void update(UserDto user) {
        openWrite();
        ContentValues values = new ContentValues();
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("nickName", user.getNickName());
        values.put("email", user.getEmail());
        values.put("passwords", user.getPassword());
        values.put("preference", user.getPreference().toString());

        database.update("User", values, "id=?", new String[]{user.getId()});

        close();
    }



    @Override
    public void delete(String id) {
        openWrite();
        database.delete("User", "id=?", new String[]{id});
        close();
    }

    @Override
    public FavouriteDto getFavourite(String userId, String noteId) {
        openRead();

        Cursor cursor = database.rawQuery("SELECT * FROM favourite WHERE userId = ? and noteId = ?", new String[]{userId, noteId});

        close();

        if (cursor != null) {
            cursor.moveToFirst();
            FavouriteDto favourite = UserDto.cursorToFavourite(cursor);
            cursor.close();
            return favourite;
        }
        return null;
    }

    @Override
    public void addFavourite(FavouriteDto favouriteDto) {
        openWrite();
        ContentValues values = new ContentValues();
        values.put("id", favouriteDto.getId());
        values.put("userId", favouriteDto.getUserId());
        values.put("noteId", favouriteDto.getNoteId());

        database.insert("favourite", null, values);

        close();
    }

    @Override
    public void deleteFavourite(String id) {
        openWrite();
        database.delete("favourite", "id=?", new String[]{id});
        close();
    }

    @Override
    public List<NoteDto> favouriteNotes(String userId) {
        openRead();
        List<NoteDto> notes = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT n.* FROM note n, favourite f WHERE n.id = f.noteId and f.userId = ?", new String[]{userId});
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            NoteDto note = NoteDto.cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
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
