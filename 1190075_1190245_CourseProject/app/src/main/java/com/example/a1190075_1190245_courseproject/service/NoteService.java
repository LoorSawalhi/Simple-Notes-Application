package com.example.a1190075_1190245_courseproject.service;

import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.NoteTagDto;
import com.example.a1190075_1190245_courseproject.dto.TagDto;

import java.util.List;

public interface NoteService {
    List<NoteDto> listAll();
    List<NoteDto> listUserNotes(String userId);
    void addNote(NoteDto noteDto);
    void updateNote(String id, NoteDto noteDto);
    void deleteNote(String id);
    void addTagToNote(NoteTagDto noteTagDto);
    void deleteTagFromNote(NoteTagDto noteTagDto);
    List<NoteDto> getAllNotesByTagsForUser(List<String> tagIds, String userId);
    List<TagDto> getAllTags();
}
