package com.example.a1190075_1190245_courseproject.dto;

import android.annotation.SuppressLint;
import android.database.Cursor;

import java.util.UUID;

public class TagDto {
    private String id = UUID.randomUUID().toString();
    private String label;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @SuppressLint("Range")
    public static TagDto cursorToTag(Cursor cursor) {
        if(cursor.getCount() == 0)
            return null;

        TagDto tag = new TagDto();

        tag.setId(cursor.getString(cursor.getColumnIndex("id")));
        tag.setLabel(cursor.getString(cursor.getColumnIndex("label")));
        return tag;
    }

}
