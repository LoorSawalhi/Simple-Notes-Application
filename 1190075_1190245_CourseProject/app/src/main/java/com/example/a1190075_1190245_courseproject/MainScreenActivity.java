package com.example.a1190075_1190245_courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
import com.example.a1190075_1190245_courseproject.dao.impl.NoteDaoImpl;
import com.example.a1190075_1190245_courseproject.dao.impl.UserDaoImpl;
import com.example.a1190075_1190245_courseproject.dto.UserDto;
import com.example.a1190075_1190245_courseproject.helpers.AppComponent;
import com.example.a1190075_1190245_courseproject.helpers.AppModule;
import com.example.a1190075_1190245_courseproject.helpers.DatabaseHelper;
import com.example.a1190075_1190245_courseproject.menu.FavouriteFragment;
import com.example.a1190075_1190245_courseproject.menu.MainPageFragment;
import com.example.a1190075_1190245_courseproject.menu.ProfileFragment;
import com.example.a1190075_1190245_courseproject.menu.SortingFragment;
import com.example.a1190075_1190245_courseproject.service.NoteService;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private SearchView searchView;

    public static UserDto currentUser;

    @Inject
    UserDao userDao;
    @Inject
    NoteDao noteDao;
    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;

    MainPageFragment mainPageFragment;
    private AppComponent appComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        appComponent =
//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        noteDao = new NoteDaoImpl(databaseHelper);
//        userDao = new UserDaoImpl(databaseHelper);
//        userService = new UserServiceImpl(userDao, noteDao);
//        noteService = new NoteServiceImpl(noteDao);

        mainPageFragment = new MainPageFragment(noteService.listAll());

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        searchView = findViewById(R.id.search_bar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {

            loadFragment(mainPageFragment);
            navigationView.setCheckedItem(R.id.all);
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all:
                loadFragment(mainPageFragment);
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.favourite:
                loadFragment(new FavouriteFragment());
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.sotred:
                loadFragment(new SortingFragment());
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.profile:
                loadFragment(new ProfileFragment());
                searchView.setVisibility(View.INVISIBLE);
                break;
            case R.id.logout:
                Intent intent = new Intent(MainScreenActivity.this, SignInActivity.class);
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
}
