package com.example.a1190075_1190245_courseproject.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.TagDto;
import com.example.a1190075_1190245_courseproject.helpers.DatabaseHelper;
import com.example.a1190075_1190245_courseproject.query.NoteSqlQuery;

import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl implements NoteDao {
    private SQLiteDatabase database;
    private final DatabaseHelper databaseHelper;

    public NoteDaoImpl(DatabaseHelper databaseHelper) {
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
        openRead();
        List<NoteDto> noteList = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM note WHERE 1 ");

        if (query.getId() != null && !query.getId().isEmpty()) {
            sql.append(" AND userId IN (");
            for (int i = 0; i < query.getId().size(); i++) {
                sql.append("?");
                if (i < query.getId().size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");
        }

        if (query.getTitle() != null && !query.getTitle().isEmpty()) {
            sql.append(" AND title LIKE ?");
        }

        if (query.getContent() != null && !query.getContent().isEmpty()) {
            sql.append(" AND content LIKE ?");
        }

        if (query.getOrderBy() != null && !query.getOrderBy().isEmpty()) {
            sql.append(" ORDER BY ").append(query.getOrderBy());
        }

        System.out.println(sql);
        Cursor cursor = database.rawQuery(sql.toString(), getQueryArgs(query));


        if (cursor.moveToFirst()) {
            do {
                NoteDto note = NoteDto.cursorToNote(cursor);
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        return noteList;
    }


    private String[] getQueryArgs(NoteSqlQuery query) {
        List<String> argsList = new ArrayList<>();

        if (query.getId() != null && !query.getId().isEmpty()) {
            argsList.addAll(query.getId());
        }

        if (query.getTitle() != null && !query.getTitle().isEmpty()) {
            argsList.add("%" + query.getTitle() + "%");
        }

        if (query.getContent() != null && !query.getContent().isEmpty()) {
            argsList.add("%" + query.getContent() + "%");
        }

        return argsList.toArray(new String[0]);
    }

    @Override

    public int insert(NoteDto note) {
        openWrite();

        ContentValues  values = new ContentValues();
        values.put("id", note.getId());
        values.put("userId", note.getUserId());
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("creationDate", note.getCreatedOn());

        long rowId = database.insert("note", null, values);
        close();
        return (int) rowId;
    }

    @Override
    public int update(String id, NoteDto noteDto) {
        openWrite();

        ContentValues values = new ContentValues();

        if (noteDto.getTitle() != null && !noteDto.getTitle().isEmpty()) {
            values.put("title", noteDto.getTitle());
        }

        if (noteDto.getContent() != null && !noteDto.getContent().isEmpty()) {
            values.put("content", noteDto.getContent());
        }

        values.put("isFavourite", noteDto.isFavourite());

        if (noteDto.getTagId() != null && !noteDto.getTagId().isEmpty()) {
            values.put("tagId", noteDto.getTagId());
        }

        if (values.size() > 0) {
            database.update("note", values, "id = ?", new String[]{id});
        }



        long rows = database.update("note", values, "id = ?", new String[]{id});
        close();
        return (int) rows;
    }

    @Override
    public int  delete(String id) {
        openWrite();

        int rows = database.delete("note", "id = ?", new String[]{id});
        close();
        return (int) rows;
    }

    @Override
    public int addTag(TagDto tagDto) {
        openWrite();

        ContentValues values = new ContentValues();
        values.put("id", tagDto.getId());
        values.put("label", tagDto.getLabel());
        values.put("userId", tagDto.getUserId());

        long rowId = database.insert("tag", null, values);
        close();
        return (int) rowId;
    }

    @Override
    public int deleteTag(String id) {
        openWrite();

        long rows = database.delete("tag", "id = ?", new String[]{id});
        close();
        return (int) rows;
    }


    @Override
    public List<NoteDto> getNotesByTagLabel(String tagLabel, String userId) {
        openRead();
        List<NoteDto> noteList = new ArrayList<>();

        String sql = "SELECT n.* FROM note n " +
                "INNER JOIN tag t ON n.tagId = t.id " +
                "WHERE n.userId = ? AND t.label = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{userId, tagLabel});

        if (cursor.moveToFirst()) {
            do {
                NoteDto note = NoteDto.cursorToNote(cursor);
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        return noteList;
    }

    @Override
    public List<TagDto> getAllTagsForUser(String userId) {
        openWrite();
        List<TagDto> tagList = new ArrayList<>();

        String sql = "SELECT t.* FROM tag t " +
                "WHERE t.userId = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{userId});

        if (cursor.moveToFirst()) {
            do {
                TagDto tag = TagDto.cursorToTag(cursor);

                tagList.add(tag);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        System.out.println(tagList);
        return tagList;
    }

    @Override
    public int setFavourite(String userId, String noteId, boolean isFavourite) {
        openWrite();
        ContentValues values = new ContentValues();
        values.put("isFavourite", isFavourite ? 1 : 0);

        long rows = database.update("note", values, "userId = ? AND id = ?", new String[]{userId, noteId});
        close();
        return (int) rows;
    }
}
