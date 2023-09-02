package com.example.a1190075_1190245_courseproject.dao;

import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.UserDto;
import com.example.a1190075_1190245_courseproject.query.UserSqlQuery;

import java.util.List;


public interface UserDao {

    List<UserDto> list();
    UserDto findById(String id);
    void insert(UserDto user);
    void update(UserDto user);
    void delete(String id);
    List<NoteDto> getUserNotes(String userId);
    List<NoteDto> getUserFavouriteNotes(String userId);
}
