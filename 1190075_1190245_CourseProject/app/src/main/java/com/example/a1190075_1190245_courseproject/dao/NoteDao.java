package com.example.a1190075_1190245_courseproject.dao;

import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.TagDto;
import com.example.a1190075_1190245_courseproject.query.NoteSqlQuery;

import java.util.List;

public interface NoteDao {

    List<NoteDto> list(NoteSqlQuery query);
    int insert(NoteDto note);
    int update(String id, NoteDto noteDto);
    int delete(String id);
    int addTag(TagDto tagDto);
    List<NoteDto> getNotesByTagLabel(String tagLabel, String userId);
    List<TagDto> getAllTagsForUser(String userId);
    int setFavourite(String userId, String noteId, boolean isFavourite);

}
