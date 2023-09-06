package com.example.a1190075_1190245_courseproject.helpers;

import android.content.Context;

import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dao.impl.NoteDaoImpl;
import com.example.a1190075_1190245_courseproject.dao.impl.UserDaoImpl;
import com.example.a1190075_1190245_courseproject.service.NoteService;
import com.example.a1190075_1190245_courseproject.service.UserService;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper(@ApplicationContext Context context) {
        return new DatabaseHelper(context);
    }

    @Provides
    @Singleton
    public UserDao provideUserDao(DatabaseHelper databaseHelper) {
        return new UserDaoImpl(databaseHelper);
    }

    @Provides
    @Singleton
    public NoteDao provideNoteDao(DatabaseHelper databaseHelper) {
        return new NoteDaoImpl(databaseHelper);
    }

    @Provides
    @Singleton
    public UserService provideUserService(UserDao userDao, NoteDao noteDao) {
        return new UserServiceImpl(userDao, noteDao);
    }

    @Provides
    @Singleton
    public NoteService provideNoteService(NoteDao noteDao) {
        return new NoteServiceImpl(noteDao);
    }
}
