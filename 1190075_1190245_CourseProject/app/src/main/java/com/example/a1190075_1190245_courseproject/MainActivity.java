package com.example.a1190075_1190245_courseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a1190075_1190245_courseproject.dao.UserDao;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class MainActivity extends AppCompatActivity {

    @Inject
    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    }
}