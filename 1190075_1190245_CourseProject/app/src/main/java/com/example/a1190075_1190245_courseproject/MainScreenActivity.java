package com.example.a1190075_1190245_courseproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.UserDto;
import com.example.a1190075_1190245_courseproject.menu.CategorisationFragment;
import com.example.a1190075_1190245_courseproject.menu.FavouriteFragment;
import com.example.a1190075_1190245_courseproject.menu.MainPageFragment;
import com.example.a1190075_1190245_courseproject.menu.ProfileFragment;
import com.example.a1190075_1190245_courseproject.menu.SortingFragment;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;


public class MainScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private SearchView searchView;
    @SuppressLint("StaticFieldLeak")
    private static TextView user;
    @SuppressLint("StaticFieldLeak")
    private static TextView email;
    public static UserDto currentUser;
    private int itemId = R.id.all;
    @Inject
    UserDao userDao;
    @Inject
    NoteDao noteDao;
    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;
    public static int[] colorArray = {
            Color.parseColor("#FFCB75E3"),
            Color.parseColor("#FF75B0E3"),
            Color.parseColor("#FFE37598"),
            Color.parseColor("#FF75E380"),
            Color.parseColor("#FFE3A875"),
            Color.parseColor("#FF8D75E3")
    };

    MainPageFragment mainPageFragment;
    FavouriteFragment favouriteFragment;
    SortingFragment sortingFragment;
    CategorisationFragment categorisationFragment;

    public static void setCurrentUser(UserDto currentUser) {
        MainScreenActivity.currentUser = currentUser;
        MainScreenActivity.user.setText(currentUser.getNickName());
        MainScreenActivity.email.setText(currentUser.getEmail());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((MyApplication) getApplication()).getAppComponent().inject(this);


        mainPageFragment = new MainPageFragment();
        favouriteFragment = new FavouriteFragment();
        sortingFragment = new SortingFragment();
        categorisationFragment = new CategorisationFragment();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        searchView = findViewById(R.id.search_bar);

        View headerView = navigationView.getHeaderView(0);
        user = headerView.findViewById(R.id.userName);
        email = headerView.findViewById(R.id.user_ID);

        user.setText(currentUser.getNickName());
        email.setText(currentUser.getEmail());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {

            loadFragment(mainPageFragment);
            navigationView.setCheckedItem(R.id.all);
        }

        navigationView.setNavigationItemSelectedListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void filterList(String query) {
        List<NoteDto> filteredList;

        switch (itemId) {
            case R.id.all:
                filteredList = filter(query, noteService.listUserNotes(MainScreenActivity.currentUser.getId()));
                updateMainPageFragment(filteredList);
                break;
            case R.id.favourite:
                filteredList = filter(query, userService.getUserFavouriteNotes(MainScreenActivity.currentUser.getId()));
                updateFavouriteFragment(filteredList);
                break;
            case R.id.sotred:
                if(MainScreenActivity.currentUser.getPreference().name().equals("CREATIONDATE")) {
                    filteredList = filter(query,  noteService.getSorted(MainScreenActivity.currentUser.getId(), "creationDate DESC"));

                } else {
                    filteredList = filter(query,  noteService.getSorted(MainScreenActivity.currentUser.getId(), "title"));

                }
                updateSortingFragment(filteredList);
                break;
            case R.id.note_tagging:
                filteredList = filter(query, categorisationFragment.getList());
                updateCategorisationFragment(filteredList);
                break;
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        itemId = item.getItemId();

        switch (itemId) {
            case R.id.all:
                loadFragment(mainPageFragment);
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.favourite:
                loadFragment(favouriteFragment);
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.sotred:
                loadFragment(sortingFragment);
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.profile:
                loadFragment(new ProfileFragment());
                searchView.setVisibility(View.INVISIBLE);
                break;
            case R.id.note_tagging:
                loadFragment(categorisationFragment);
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.logout:
                Intent intent = new Intent(MainScreenActivity.this, SignInActivity.class);
                MainScreenActivity.currentUser = null;
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private List<NoteDto> filter(String query, List<NoteDto> originalList) {
        List<NoteDto> filteredList = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            String lowercaseQuery = query.toLowerCase(Locale.ROOT);

            for (NoteDto noteDto : originalList) {
                if (noteDto.getTitle().toLowerCase(Locale.ROOT).contains(lowercaseQuery) ||
                        noteDto.getContent().toLowerCase(Locale.ROOT).contains(lowercaseQuery)) {
                    filteredList.add(noteDto);
                }
            }
        }

        return filteredList;
    }

    private void updateMainPageFragment(List<NoteDto> filteredList) {

        if (mainPageFragment != null) {
            mainPageFragment.updateList(filteredList);
        }
    }

    private void updateFavouriteFragment(List<NoteDto> filteredList) {

        if (favouriteFragment != null) {
            favouriteFragment.updateList(filteredList);
        }
    }

    private void updateSortingFragment(List<NoteDto> filteredList) {

        if (sortingFragment != null) {
            sortingFragment.updateList(filteredList);
        }
    }

    private void updateCategorisationFragment(List<NoteDto> filteredList) {

        if (categorisationFragment != null) {
            categorisationFragment.updateList(filteredList);
        }
    }

}
