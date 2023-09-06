package com.example.a1190075_1190245_courseproject.service;

import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    UserDto findById(String id);
    boolean addUser(UserDto user);
    void updateUser(UserDto user);
    void deleteUser(String id);
    List<NoteDto> getUserNotes(String userId);
    List<NoteDto> getUserFavouriteNotes(String userId);
    void addFavourite(String userId, String noteId);
    boolean authorized(String email, String password);
    UserDto findUserByEmail(String email);
}
