package com.example.a1190075_1190245_courseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.a1190075_1190245_courseproject.menu.FavouriteFragment;
import com.example.a1190075_1190245_courseproject.menu.MainPageFragment;
import com.example.a1190075_1190245_courseproject.menu.SortingFragment;
import com.google.android.material.navigation.NavigationView;

public class MainScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainPageFragment()).commit();
            navigationView.setCheckedItem(R.id.all);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainPageFragment()).commit();
                break;
            case R.id.favourite:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouriteFragment()).commit();
                break;
            case R.id.sotred:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SortingFragment()).commit();
                break;
//            case R.id.logout:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SignInActivity()).commit();
//                break;
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
}