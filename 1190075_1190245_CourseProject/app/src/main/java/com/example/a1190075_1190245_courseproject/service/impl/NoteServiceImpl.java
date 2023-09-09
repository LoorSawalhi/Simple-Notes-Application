package com.example.a1190075_1190245_courseproject.service.impl;

import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.TagDto;
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
    public List<NoteDto> listUserNotes(String userId) {
        NoteSqlQuery query = new NoteSqlQuery();
        query.setId(List.of(userId));
        query.setOrderBy("creationDate");
        return noteDao.list(query);
    }

    @Override
    public int addNote(NoteDto noteDto) {
        return noteDao.insert(noteDto);
    }

    @Override
    public int updateNote(String id, NoteDto noteDto) {
        return noteDao.update(id, noteDto);
    }

    @Override
    public int deleteNote(String id) {
        return noteDao.delete(id);
    }

    @Override
    public int setFavourite(String userId, String noteId) {
        return noteDao.setFavourite(userId, noteId, true);
    }

    @Override
    public int deleteFavourite(String userId, String noteId) {
        return noteDao.setFavourite(userId, noteId, false);
    }

    @Override
    public int addTag(TagDto tagDto) {
        return noteDao.addTag(tagDto);
    }


    @Override
    public List<NoteDto> getNotesByTagLabel(String tagLabel, String userId) {
        return noteDao.getNotesByTagLabel(tagLabel, userId);
    }

    @Override
    public List<TagDto> getAllTagsForUser(String userId) {
        return noteDao.getAllTagsForUser(userId);
    }

    @Override
    public List<NoteDto> getSorted(String userId, String sortingCriteria) {
        NoteSqlQuery query = new NoteSqlQuery();
        query.setId(List.of(userId));
        query.setOrderBy(sortingCriteria);
        System.out.println(noteDao.list(query));
        return noteDao.list(query);
    }

}
