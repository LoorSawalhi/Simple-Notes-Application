package com.example.a1190075_1190245_courseproject;

import android.content.Intent;
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

import java.util.List;

import javax.inject.Inject;


public class MainScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private SearchView searchView;
    private static TextView user;
    private static TextView email;
    public static UserDto currentUser;

    @Inject
    UserDao userDao;
    @Inject
    NoteDao noteDao;
    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;
    private List<NoteDto> favNotes;

    MainPageFragment mainPageFragment;
    FavouriteFragment favouriteFragment;
    SortingFragment sortingFragment;

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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
                loadFragment(new CategorisationFragment());
                searchView.setVisibility(View.VISIBLE);
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
