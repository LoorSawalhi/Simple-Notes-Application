package com.example.a1190075_1190245_courseproject;

import android.app.Application;

import com.example.a1190075_1190245_courseproject.helpers.AppComponent;
import com.example.a1190075_1190245_courseproject.helpers.AppModule;
import com.example.a1190075_1190245_courseproject.helpers.DaggerAppComponent;


public class MyApplication extends Application {


    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
