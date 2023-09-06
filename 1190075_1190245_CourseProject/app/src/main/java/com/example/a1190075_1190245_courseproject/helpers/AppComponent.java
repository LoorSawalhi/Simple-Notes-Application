package com.example.a1190075_1190245_courseproject.helpers;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.menu.FavouriteFragment;
import com.example.a1190075_1190245_courseproject.menu.MainPageFragment;
import com.example.a1190075_1190245_courseproject.menu.ProfileFragment;
import com.example.a1190075_1190245_courseproject.menu.SortingFragment;

import javax.inject.Singleton;

import dagger.Component;

//@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainScreenActivity activity);
    void inject(MainPageFragment fragment);
    void inject(ProfileFragment profileFragment);
    void inject(SortingFragment sortingFragment);
    void inject(FavouriteFragment favouriteFragment);
}