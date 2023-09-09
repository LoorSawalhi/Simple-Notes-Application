package com.example.a1190075_1190245_courseproject.dao;

import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.UserDto;

import java.util.List;


public interface UserDao {

    List<UserDto> list();
    boolean insert(UserDto user);
    int update(UserDto user);
    List<NoteDto> favouriteNotes(String userId);
}
