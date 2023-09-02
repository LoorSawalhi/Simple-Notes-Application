package com.example.a1190075_1190245_courseproject.dto;

import java.util.Date;
import java.util.UUID;

public class NoteDto {
    private String id = UUID.randomUUID().toString();
    private String userId;
    private String title;
    private String text;
    private Date createdOn = new Date();

    public NoteDto(String userId, String title, String text, Date createdOn) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.createdOn = createdOn;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
