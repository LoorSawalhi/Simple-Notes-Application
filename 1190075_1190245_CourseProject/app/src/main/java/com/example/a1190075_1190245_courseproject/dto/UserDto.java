package com.example.a1190075_1190245_courseproject.dto;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.example.a1190075_1190245_courseproject.enums.Preference;

import java.util.UUID;


public class UserDto {

    private String id = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private String password;
    private Preference preference;

    public UserDto() {

    }

    public UserDto(String firstName, String lastName, String nickName, String email, String password, Preference preference) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.preference = preference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    @SuppressLint("Range")
    public static UserDto cursorToUser(Cursor cursor) {
        UserDto user = new UserDto();
        user.setId(cursor.getString(cursor.getColumnIndex("id")));
        user.setFirstName(cursor.getString(cursor.getColumnIndex("firstName")));
        user.setLastName(cursor.getString(cursor.getColumnIndex("lastName")));
        user.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
        user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        user.setPassword(cursor.getString(cursor.getColumnIndex("passwords")));

        if(cursor.getString(cursor.getColumnIndex("preference")).equals(Preference.ALPHABETICALLY.toString())) {
            user.setPreference(Preference.ALPHABETICALLY);
        } else {
            user.setPreference(Preference.CREATION_DATE);
        }
        return user;
    }

    @SuppressLint("Range")
    public static FavouriteDto cursorToFavourite(Cursor cursor) {
        FavouriteDto favouriteDto = new FavouriteDto();
        favouriteDto.setId(cursor.getString(cursor.getColumnIndex("id")));
        favouriteDto.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
        favouriteDto.setNoteId(cursor.getString(cursor.getColumnIndex("noteId")));
        return favouriteDto;
    }
}
