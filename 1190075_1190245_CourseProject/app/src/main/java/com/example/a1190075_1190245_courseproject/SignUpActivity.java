package com.example.a1190075_1190245_courseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView linkTextView = findViewById(R.id.linkTextView);
        TextView firstName = findViewById(R.id.firstNameField);
        TextView lastName = findViewById(R.id.lastNameField);
        EditText email = findViewById(R.id.emailField);
        EditText password = findViewById(R.id.passwordField);
        EditText passwordConfirm = findViewById(R.id.confirtPasswordField);
        Button createAccount = findViewById(R.id.creatAccount);

        linkTextView.setOnClickListener(v -> {

            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });


    }
}