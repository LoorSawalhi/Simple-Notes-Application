package com.example.a1190075_1190245_courseproject.service;

import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.TagDto;

import java.util.List;

public interface NoteService {
    List<NoteDto> listUserNotes(String userId);
    int addNote(NoteDto noteDto);
    int updateNote(String id, NoteDto noteDto);
    int deleteNote(String id);
    int setFavourite(String userId, String noteId);
    int deleteFavourite(String userId, String noteId);
    int addTag(TagDto tagDto);
    List<NoteDto> getNotesByTagLabel(String tagLabel, String userId);
    List<TagDto> getAllTagsForUser(String userId);
    List<NoteDto> getSorted(String userId, String sortingCriteria);
}
