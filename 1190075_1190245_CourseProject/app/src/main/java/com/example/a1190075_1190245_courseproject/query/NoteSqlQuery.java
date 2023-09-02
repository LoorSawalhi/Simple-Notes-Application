package com.example.a1190075_1190245_courseproject.query;

import java.util.ArrayList;
import java.util.List;

public class NoteSqlQuery {
    private List<String> id = new ArrayList<>();
    private String title;
    private String content;

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
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
}
