package com.example.a1190075_1190245_courseproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dao.impl.NoteDaoImpl;
import com.example.a1190075_1190245_courseproject.dao.impl.UserDaoImpl;
import com.example.a1190075_1190245_courseproject.dto.UserDto;
import com.example.a1190075_1190245_courseproject.helpers.DatabaseHelper;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class SignUpActivity extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView password;
    private TextView passwordConfirm;
    private Button createAccount;


    @Inject
    UserDao userDao;
    @Inject
    NoteDao noteDao;
    @Inject
    public UserServiceImpl userService;


    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,12}$";

    //TODO: PRINT TO THE USER PASSWORD REQUIREMENTS

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

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        noteDao = new NoteDaoImpl(databaseHelper);
        userDao = new UserDaoImpl(databaseHelper);
        userService = new UserServiceImpl(userDao, noteDao);

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
                UserDto user = new UserDto();
                user.setFirstName(firstName.getText().toString());
                user.setLastName(lastName.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                user.setNickName(String.format("%s %s", firstName.getText().toString(), lastName.getText().toString()));

                if(userService.addUser(user)) {
                    Toast.makeText(this, "Added Successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, MainScreenActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Email already exists!", Toast.LENGTH_LONG).show();
                    email.setText("");
                }

            } else {
                changeColors();
            }
        });
    }

    //TODO: Change the stroke color of the fiels
    private void changeColors() {
        boolean flag = false;
        int i =0;
        String toastMessage = "";
         if (!isValidEmail(email.getText().toString())) {
             email.setTextColor(Color.RED);
             toastMessage += "Email Format Is Wrong!\n";
             flag = true;
         } else {
             email.setTextColor(Color.BLACK);
         }

         if (!isValidPassword(password.getText().toString()) || !passwordConfirm.getText().toString().equals(password.getText().toString())){
             passwordConfirm.setTextColor(Color.RED);
             password.setTextColor(Color.RED);
             if (!isValidPassword(password.getText().toString())){
                 toastMessage += "Password Should Contain 6-12 Characters with capital & small letters and numbers\n";
             } else {
                 toastMessage += "Passwords Don't Match\n";
             }
             flag = true;
         } else {
             passwordConfirm.setTextColor(Color.BLACK);
             password.setTextColor(Color.BLACK);
         }

         if(!isValidName(firstName.getText().toString())){
             firstName.setTextColor(Color.RED);
             flag = true;
             i=1;
             toastMessage += "Names Should be 3-10 Alphabets";
         } else {
             firstName.setTextColor(Color.BLACK);
         }

        if(!isValidName(lastName.getText().toString())){
            lastName.setTextColor(Color.RED);
            if (i == 0){
                toastMessage += "Names Should be 3-10 Alphabets";
            }
            flag = true;
         } else {
            lastName.setTextColor(Color.BLACK);

        }

        if (flag){
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
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
        if(!(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()
                || passwordConfirm.getText().toString().isEmpty() || firstName.getText().toString().isEmpty()
                || lastName.getText().toString().isEmpty())){
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