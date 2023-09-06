package com.example.a1190075_1190245_courseproject.service.impl;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dto.FavouriteDto;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.UserDto;
import com.example.a1190075_1190245_courseproject.query.NoteSqlQuery;
import com.example.a1190075_1190245_courseproject.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

public class UserServiceImpl implements UserService {


    UserDao userDao;

    NoteDao noteDao;

    @Inject
    public UserServiceImpl(UserDao userDao, NoteDao noteDao) {
        this.userDao = userDao;
        this.noteDao = noteDao;
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.list();
    }

    @Override
    public UserDto findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public boolean addUser(UserDto user) {
        return userDao.insert(user);
    }

    @Override
    public void updateUser(UserDto user) {
        userDao.update(user);
    }

    @Override
    public void deleteUser(String id) {
        userDao.delete(id);
    }

    @Override
    public List<NoteDto> getUserNotes(String userId) {
        List<NoteDto> notes = noteDao.list(new NoteSqlQuery());
        return notes.stream().filter(note -> note.getUserId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public List<NoteDto> getUserFavouriteNotes(String userId) {
        return userDao.favouriteNotes(userId);
    }


    @Override
    public void addFavourite(String userId, String noteId) {
        FavouriteDto favouriteDto = new FavouriteDto();
        favouriteDto.setNoteId(noteId);
        favouriteDto.setUserId(userId);
        System.out.println("HERE");
        userDao.addFavourite(favouriteDto);
    }

    @Override
    public void deleteFavourite(String userId, String noteId) {
        userDao.deleteFavourite(userId, noteId);
    }

    @Override
    public FavouriteDto getFavourite(String userId, String noteId) {
        return userDao.getFavourite(userId, noteId);
    }
    @Override
    public boolean authorized(String email, String password) {
        UserDto user = userDao.loginUser(email, password);
        if(user != null) {
            MainScreenActivity.currentUser = user;
            return true;
        }
        return false;
    }

    @Override
    public UserDto findUserByEmail(String email) {
        List<UserDto> users = userDao.list();
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }
}
