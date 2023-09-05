package com.example.a1190075_1190245_courseproject.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;

import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.NoteTagDto;
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
            sql.append(" AND id IN (");
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

    public void insert(NoteDto note) {
        openWrite();

        ContentValues  values = new ContentValues();
        values.put("id", note.getId());
        values.put("userId", note.getUserId());
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("creationDate", note.getCreatedOn());

        database.insert("note", null, values);
        close();
    }

    @Override
    public void update(String id, NoteDto noteDto) {
        openWrite();

        ContentValues values = new ContentValues();
        values.put("title", noteDto.getTitle());
        values.put("content", noteDto.getContent());

        database.update("note", values, "id = ?", new String[]{id});
        close();
    }

    @Override
    public void delete(String id) {
        openWrite();

        database.delete("note", "id = ?", new String[]{id});
        close();
    }

    @Override
    public void addTag(TagDto tagDto) {
        openWrite();

        ContentValues values = new ContentValues();
        values.put("id", tagDto.getId());
        values.put("label", tagDto.getLabel());

        database.insert("tag", null, values);
        close();
    }

    @Override
    public void deleteTag(String id) {
        openWrite();

        database.delete("tag", "id = ?", new String[]{id});
        close();
    }

    @Override
    public void addNoteTag(NoteTagDto noteTagDto) {
        openWrite();

        ContentValues values = new ContentValues();
        values.put("id", noteTagDto.getId());
        values.put("noteId", noteTagDto.getNoteId());
        values.put("tagId", noteTagDto.getTagId());
        values.put("userId", noteTagDto.getUserId());

        database.insert("note_tag", null, values);
        close();
    }

    @Override
    public void deleteNoteTag(NoteTagDto noteTagDto) {
        openWrite();
        database.delete("note_tag", "id = ?", new String[]{noteTagDto.getTagId()});
        close();
    }

    @Override
    public List<NoteDto> getNotesByTagsForUser(List<String> tagIds, String userId) {
        List<NoteDto> noteList = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT n.* FROM note n " +
                        "INNER JOIN note_tag nt ON n.id = nt.noteId " +
                        "WHERE nt.userId = ? "
        );

        if (tagIds != null && !tagIds.isEmpty()) {
            sql.append("AND nt.tagId IN (");
            for (int i = 0; i < tagIds.size(); i++) {
                sql.append("?");
                if (i < tagIds.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");
        }

        sql.append(" GROUP BY n.id");

        List<String> argsList = new ArrayList<>();
        argsList.add(userId);
        if (tagIds != null && !tagIds.isEmpty()) {
            argsList.addAll(tagIds);
        }

        Cursor cursor = database.rawQuery(sql.toString(), argsList.toArray(new String[0]));

        if (cursor.moveToFirst()) {
            do {
                NoteDto note = NoteDto.cursorToNote(cursor);
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return noteList;
    }

    @Override
    public List<TagDto> getAllTags() {
        List<TagDto> tagList = new ArrayList<>();

        String sql = "SELECT * FROM tag";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                TagDto tag = TagDto.cursorToTag(cursor);

                tagList.add(tag);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return tagList;
    }

}
