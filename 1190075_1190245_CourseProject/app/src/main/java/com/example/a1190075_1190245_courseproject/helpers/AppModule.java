package com.example.a1190075_1190245_courseproject.helpers;

import android.content.Context;

import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dao.impl.NoteDaoImpl;
import com.example.a1190075_1190245_courseproject.dao.impl.UserDaoImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
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
}
