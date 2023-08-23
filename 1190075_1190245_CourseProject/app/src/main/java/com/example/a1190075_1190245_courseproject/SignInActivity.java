package com.example.a1190075_1190245_courseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        CheckBox rememberUser = findViewById(R.id.remember_me);
        Button logIn = findViewById(R.id.login);
        TextView signUpPage = findViewById(R.id.linkSignUp);

        signUpPage.setOnClickListener(v -> {

            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

    }
}