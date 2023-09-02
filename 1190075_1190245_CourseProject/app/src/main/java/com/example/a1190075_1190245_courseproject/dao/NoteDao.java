package com.example.a1190075_1190245_courseproject.dao;

import com.example.a1190075_1190245_courseproject.query.NoteSqlQuery;

import java.util.List;

public interface NoteDao {

    List<NoteDao> list(NoteSqlQuery query);
    void insert(NoteDao note);
    void update(String id, NoteDao noteDao);
    void delete(String id);
}
