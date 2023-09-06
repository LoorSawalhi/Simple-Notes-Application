package com.example.a1190075_1190245_courseproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView password;
    private TextView passwordConfirm;
    private Button createAccount;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,12}$";


    private static final Pattern pattern_email = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern pattern_password = Pattern.compile(PASSWORD_PATTERN);

    private final TextWatcher textWatcher = new TextWatcher() {
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
        setContentView(R.layout.activity_sign_up);

        TextView linkTextView = findViewById(R.id.linkTextView);
        firstName = findViewById(R.id.firstNameField);
        lastName = findViewById(R.id.lastNameField);
        email = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);
        passwordConfirm = findViewById(R.id.confirmPasswordField);
        createAccount = findViewById(R.id.createAccount);

        firstName.addTextChangedListener(textWatcher);
        lastName.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        passwordConfirm.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);


        linkTextView.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        createAccount.setOnClickListener(v -> {
            if(checkFields()){



                Intent intent = new Intent(SignUpActivity.this, MainScreenActivity.class);
                startActivity(intent);
            } else {
                changeColors();
            }
        });
    }

    private void changeColors() {
        boolean flag = false;
         if (!isValidEmail(email.getText().toString())) {
             email.setTextColor(Color.RED);
             flag = true;
         } else {
             email.setTextColor(Color.BLACK);
         }

         if (!isValidPassword(password.getText().toString()) || !passwordConfirm.getText().toString().equals(password.getText().toString())){
             passwordConfirm.setTextColor(Color.RED);
             password.setTextColor(Color.RED);
             flag = true;
         } else {
             passwordConfirm.setTextColor(Color.BLACK);
             password.setTextColor(Color.BLACK);
         }

         if(!isValidName(firstName.getText().toString())){
             firstName.setTextColor(Color.RED);
             flag = true;
         } else {
             firstName.setTextColor(Color.BLACK);
         }

        if(!isValidName(lastName.getText().toString())){
            lastName.setTextColor(Color.RED);
            flag = true;
         } else {
            lastName.setTextColor(Color.BLACK);

        }

        if (flag){
            createAccount.setBackgroundResource(R.drawable.rounded_button);
            createAccount.setEnabled(false);
        }
    }

    private boolean checkFields(){

        return isValidEmail(email.getText().toString()) && isValidPassword(password.getText().toString())
                && passwordConfirm.getText().toString().equals(password.getText().toString()) && isValidName(firstName.getText().toString())
                && isValidName(lastName.getText().toString());
    }

    private void updateButtonColor() {
        if(!(email.getText().toString().isBlank() || password.getText().toString().isBlank()
                || passwordConfirm.getText().toString().isBlank() || firstName.getText().toString().isBlank()
                || lastName.getText().toString().isBlank())){
            createAccount.setBackgroundResource(R.drawable.clickable_button);
            createAccount.setEnabled(true);
        }else{
            createAccount.setBackgroundResource(R.drawable.rounded_button);
            createAccount.setEnabled(false);
        }
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern_email.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        Matcher matcher = pattern_password.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidName(String name) {
        int minLength = 3;
        int maxLength = 10;

        if (name.length() < minLength || name.length() > maxLength) {
            return false;
        }

        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
}