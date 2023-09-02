package com.example.a1190075_1190245_courseproject;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp

public class MyApplication extends Application {
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//        appComponent = DaggerActivity_MembersInjector.create()
        appComponent.inject(this);}
}
