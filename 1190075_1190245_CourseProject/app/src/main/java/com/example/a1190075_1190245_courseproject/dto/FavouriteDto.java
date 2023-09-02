package com.example.a1190075_1190245_courseproject.dto;

import java.util.UUID;

public class FavouriteDto {

    private String id = UUID.randomUUID().toString();
    private String userId;
    private String noteId;

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

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}
