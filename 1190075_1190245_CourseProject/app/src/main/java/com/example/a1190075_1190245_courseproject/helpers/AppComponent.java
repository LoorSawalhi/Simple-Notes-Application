package com.example.a1190075_1190245_courseproject.helpers;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.NoteFragment;
import com.example.a1190075_1190245_courseproject.NoteLayoutFragment;
import com.example.a1190075_1190245_courseproject.SignInActivity;
import com.example.a1190075_1190245_courseproject.SignUpActivity;
import com.example.a1190075_1190245_courseproject.SplashScreenActivity;
import com.example.a1190075_1190245_courseproject.adapter.CategoryAdapter;
import com.example.a1190075_1190245_courseproject.adapter.NewNoteAdapter;
import com.example.a1190075_1190245_courseproject.menu.CategorisationFragment;
import com.example.a1190075_1190245_courseproject.menu.FavouriteFragment;
import com.example.a1190075_1190245_courseproject.menu.MainPageFragment;
import com.example.a1190075_1190245_courseproject.menu.ProfileFragment;
import com.example.a1190075_1190245_courseproject.menu.SortingFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainScreenActivity activity);
    void inject(SignInActivity activity);
    void inject(SignUpActivity activity);
    void inject(SplashScreenActivity activity);
    void inject(MainPageFragment fragment);
    void inject(ProfileFragment profileFragment);
    void inject(SortingFragment sortingFragment);
    void inject(FavouriteFragment favouriteFragment);
    void inject(NewNoteAdapter adapter);
    void inject(NoteLayoutFragment noteLayoutFragment);

    void inject(NoteFragment noteFragment);

    void inject(CategoryAdapter categoryAdapter);

    void inject(CategorisationFragment categorisationFragment);
}