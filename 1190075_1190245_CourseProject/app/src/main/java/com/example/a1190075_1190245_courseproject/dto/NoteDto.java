package com.example.a1190075_1190245_courseproject.dto;

import android.annotation.SuppressLint;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class NoteDto {
    private String id = UUID.randomUUID().toString();
    private String userId;
    private String title;
    private String content;
    private Date createdOnDate = new Date();
    private String createdOn ;
    private boolean isFavourite;
    private String tagId;

    public NoteDto() {

    }
    public NoteDto(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedOn() {
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT);
        this.createdOn = dateFormatter.format(createdOnDate);
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @SuppressLint("Range")
    public static NoteDto cursorToNote(Cursor cursor) {

        if(cursor.getCount() == 0)
            return null;

        NoteDto note = new NoteDto();

        note.setId(cursor.getString(cursor.getColumnIndex("id")));
        note.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
        note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        note.setContent(cursor.getString(cursor.getColumnIndex("content")));
        note.setCreatedOn(cursor.getString(cursor.getColumnIndex("creationDate")));
        note.setTagId(cursor.getString(cursor.getColumnIndex("tagId")));
        note.setFavourite(cursor.getString(cursor.getColumnIndex("isFavourite")).equals("1"));

        return note;
    }
}
