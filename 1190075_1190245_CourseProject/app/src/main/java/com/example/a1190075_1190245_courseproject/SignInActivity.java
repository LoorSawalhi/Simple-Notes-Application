package com.example.a1190075_1190245_courseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    private  EditText email;
    private  EditText password;
    private CheckBox rememberUser;
    private Button logIn;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            updateButtonColor();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rememberUser = findViewById(R.id.remember_me);
        logIn = findViewById(R.id.login);
        logIn.setEnabled(false);
        TextView signUpPage = findViewById(R.id.linkSignUp);

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        signUpPage.setOnClickListener(v -> {

            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        logIn.setOnClickListener(v -> {

            Intent intent = new Intent(SignInActivity.this, MainScreenActivity.class);
            startActivity(intent);
        });

    }
    private void updateButtonColor() {
        if(!(email.getText().toString().isBlank() || password.getText().toString().isBlank()) ){

            logIn.setBackgroundResource(R.drawable.clickable_button);
            logIn.setEnabled(true);
        }else{
            logIn.setBackgroundResource(R.drawable.rounded_button);
            logIn.setEnabled(false);
        }
    }
}