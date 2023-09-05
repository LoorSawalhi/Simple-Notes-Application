package com.example.a1190075_1190245_courseproject.dao;

import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.NoteTagDto;
import com.example.a1190075_1190245_courseproject.dto.TagDto;
import com.example.a1190075_1190245_courseproject.query.NoteSqlQuery;

import java.util.List;

public interface NoteDao {

    List<NoteDto> list(NoteSqlQuery query);
    void insert(NoteDto note);
    void update(String id, NoteDto noteDto);
    void delete(String id);
    void addTag(TagDto tagDto);
    void deleteTag(String id);
    void addNoteTag(NoteTagDto noteTagDto);
    void deleteNoteTag(NoteTagDto noteTagDto);
    List<NoteDto> getNotesByTagsForUser(List<String> tagIds, String userId);
    List<TagDto> getAllTags();
}
