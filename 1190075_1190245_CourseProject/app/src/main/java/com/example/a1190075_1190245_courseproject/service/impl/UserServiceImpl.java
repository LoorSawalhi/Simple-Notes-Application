package com.example.a1190075_1190245_courseproject.service.impl;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.TagDto;
import com.example.a1190075_1190245_courseproject.dto.UserDto;
import com.example.a1190075_1190245_courseproject.query.NoteSqlQuery;
import com.example.a1190075_1190245_courseproject.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

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
        TagDto tag = new TagDto();
        tag.setLabel("All");
        tag.setUserId(user.getId());

        noteDao.addTag(tag);

        return userDao.insert(user);
    }

    @Override
    public int updateUser(UserDto user) {
        return userDao.update(user);
    }

    @Override
    public int deleteUser(String id) {
        return userDao.delete(id);
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
