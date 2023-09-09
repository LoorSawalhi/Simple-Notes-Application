package com.example.a1190075_1190245_courseproject.service;

import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.UserDto;

import java.util.List;

public interface UserService {
    boolean addUser(UserDto user);
    int updateUser(UserDto user);
    List<NoteDto> getUserFavouriteNotes(String userId);
    UserDto findUserByEmail(String email);
}
