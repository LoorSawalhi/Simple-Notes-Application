package com.example.a1190075_1190245_courseproject.service;

import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dao.impl.UserDaoImpl;

import javax.inject.Inject;

public class UserService {

    UserDao userDao;

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
