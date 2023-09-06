package com.example.a1190075_1190245_courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dao.impl.NoteDaoImpl;
import com.example.a1190075_1190245_courseproject.dao.impl.UserDaoImpl;
import com.example.a1190075_1190245_courseproject.dto.UserDto;
import com.example.a1190075_1190245_courseproject.helpers.DatabaseHelper;
import com.example.a1190075_1190245_courseproject.helpers.SharedPrefManager;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import javax.inject.Inject;

public class SignInActivity extends AppCompatActivity {

    private  EditText email;
    private  EditText password;
    private CheckBox rememberUser;
    private Button logIn;
    @Inject
    UserDao userDao;
    @Inject
    NoteDao noteDao;
    @Inject
    public UserServiceImpl userService;

    public SharedPrefManager sharedPrefManager;
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

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        noteDao = new NoteDaoImpl(databaseHelper);
        userDao = new UserDaoImpl(databaseHelper);
        userService = new UserServiceImpl(userDao, noteDao);

        sharedPrefManager = SharedPrefManager.getInstance(this);

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
            UserDto userDto = userService.findUserByEmail(email.getText().toString());

            if (userDto == null){
                Toast.makeText(this, "No Such User Exists", Toast.LENGTH_LONG).show();
                password.setText("");
                email.setText("");
            } else if(!userDto.getPassword().equals(password.getText().toString())){
                Toast.makeText(this, "Wrong Password", Toast.LENGTH_LONG).show();
                password.setText("");
            } else {
//          load notes
                MainScreenActivity.currentUser = userDto;
                //TODO save email on shared preference
                Intent intent = new Intent(SignInActivity.this, MainScreenActivity.class);
                startActivity(intent);
            }
        });

    }
    private void updateButtonColor() {
        if(!(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) ){

            logIn.setBackgroundResource(R.drawable.clickable_button);
            logIn.setEnabled(true);
        } else{
            logIn.setBackgroundResource(R.drawable.rounded_button);
            logIn.setEnabled(false);
        }
    }
}