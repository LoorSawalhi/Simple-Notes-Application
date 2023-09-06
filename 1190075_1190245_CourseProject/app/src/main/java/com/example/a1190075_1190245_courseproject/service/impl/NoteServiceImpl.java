package com.example.a1190075_1190245_courseproject.service.impl;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.NoteTagDto;
import com.example.a1190075_1190245_courseproject.dto.TagDto;
import com.example.a1190075_1190245_courseproject.menu.MainPageFragment;
import com.example.a1190075_1190245_courseproject.query.NoteSqlQuery;
import com.example.a1190075_1190245_courseproject.service.NoteService;

import java.util.List;

import javax.inject.Inject;

public class NoteServiceImpl implements NoteService {
    private final NoteDao noteDao;

    @Inject
    public NoteServiceImpl(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public List<NoteDto> listAll() {
        NoteSqlQuery query = new NoteSqlQuery();
//        query.setId(List.of(MainScreenActivity.currentUser.getId()));

        return noteDao.list(query);
    }

    @Override
    public List<NoteDto> listUserNotes(String userId) {
        NoteSqlQuery query = new NoteSqlQuery();
        query.setId(List.of(userId));

        return noteDao.list(query);
    }

    @Override
    public void addNote(NoteDto noteDto) {
        noteDao.insert(noteDto);
    }

    @Override
    public void updateNote(String id, NoteDto noteDto) {
        noteDao.update(id, noteDto);
    }

    @Override
    public void deleteNote(String id) {
        noteDao.delete(id);
    }

    @Override
    public void addTagToNote(NoteTagDto noteTagDto) {
        noteDao.addNoteTag(noteTagDto);
    }

    @Override
    public void deleteTagFromNote(NoteTagDto noteTagDto) {
        noteDao.deleteNoteTag(noteTagDto);
    }

    @Override
    public List<NoteDto> getAllNotesByTagsForUser(List<String> tagIds, String userId) {
        return noteDao.getNotesByTagsForUser(tagIds, userId);
    }

    @Override
    public List<TagDto> getAllTags() {
        return noteDao.getAllTags();
    }

    @Override
    public List<NoteDto> getSorted(String sortingCriteria) {
        NoteSqlQuery query = new NoteSqlQuery();
        query.setOrderBy(sortingCriteria);
        return noteDao.list(query);
    }
}
