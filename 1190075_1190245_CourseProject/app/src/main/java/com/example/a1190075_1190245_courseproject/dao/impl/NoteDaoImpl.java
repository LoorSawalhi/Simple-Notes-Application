package com.example.a1190075_1190245_courseproject.dao.impl;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.TagDto;
import com.example.a1190075_1190245_courseproject.helpers.DatabaseHelper;
import com.example.a1190075_1190245_courseproject.query.NoteSqlQuery;

import java.util.List;

public class NoteDaoImpl implements NoteDao {
    private SQLiteDatabase database;
    private final DatabaseHelper databaseHelper;

    public NoteDaoImpl(DatabaseHelper databaseHelper) {
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
    public List<NoteDto> list(NoteSqlQuery query) {
        return null;
    }

    @Override
    public void insert(NoteDto note) {

    }

    @Override
    public void update(String id, NoteDao noteDao) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void addTag(TagDto tagDto) {

    }

    @Override
    public void deleteTag(String id) {

    }


}
