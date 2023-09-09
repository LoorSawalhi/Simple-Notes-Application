package com.example.a1190075_1190245_courseproject.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.SignUpActivity;
import com.example.a1190075_1190245_courseproject.dao.NoteDao;
import com.example.a1190075_1190245_courseproject.dao.UserDao;
import com.example.a1190075_1190245_courseproject.dto.UserDto;
import com.example.a1190075_1190245_courseproject.helpers.SharedPrefManager;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import javax.inject.Inject;


public class ProfileFragment extends Fragment {

    @Inject
    UserDao userDao;

    @Inject
    NoteDao noteDao;
    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private ImageView editFN;
    private ImageView editLN;
    private ImageView editEmail;
    private ImageView editPassword;

    private String fName = "";
    private String lName = "";
    private String passwordText = "";
    private String emailText = "";

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        editFN = view.findViewById(R.id.edit_fn);
        editLN = view.findViewById(R.id.edit_ln);
        editEmail = view.findViewById(R.id.edit_email);
        editPassword = view.findViewById(R.id.edit_password);

        firstName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        password.setEnabled(false);

        firstName.setText(MainScreenActivity.currentUser.getFirstName());
        lastName.setText(MainScreenActivity.currentUser.getLastName());
        email.setText(MainScreenActivity.currentUser.getEmail());
        password.setText(MainScreenActivity.currentUser.getPassword());
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        editFN.setOnClickListener(v -> toggleEditText(firstName, editFN));
        editLN.setOnClickListener(v -> toggleEditText(lastName, editLN));
        editEmail.setOnClickListener(v -> toggleEditText(email, editEmail));
        editPassword.setOnClickListener(v -> toggleEditText(password, editPassword));

        fName = firstName.getText().toString();
        lName = lastName.getText().toString();
        passwordText = password.getText().toString();
        emailText = email.getText().toString();

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    private void toggleEditText(EditText editText, ImageView image) {

        if(!editText.isEnabled()){
            image.setImageResource(R.drawable.save);
            fName = firstName.getText().toString();
            lName = lastName.getText().toString();
            passwordText = password.getText().toString();
            emailText = email.getText().toString();
        } else {

            image.setImageResource(R.drawable.edit);

            switch (image.getId()) {
                case R.id.edit_fn:
                    if (!SignUpActivity.isValidName(editText.getText().toString())) {
                        editText.setText(fName);
                        Toast.makeText(getContext(), "Names Should be 3-10 Alphabets", Toast.LENGTH_LONG).show();
                    }else {
                        fName = firstName.getText().toString();
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.edit_ln:
                    if (!SignUpActivity.isValidName(editText.getText().toString())) {
                        editText.setText(lName);
                        Toast.makeText(getContext(), "Names Should be 3-10 Alphabets", Toast.LENGTH_LONG).show();
                    }else {
                        lName = lastName.getText().toString();
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.edit_email:
                    if (!SignUpActivity.isValidEmail(editText.getText().toString())) {
                        editText.setText(emailText);
                        Toast.makeText(getContext(), "Email Format Is Wrong", Toast.LENGTH_LONG).show();
                    }else {
                        emailText = email.getText().toString();
                        String saved = SharedPrefManager.getInstance(getContext()).readString("email", "");
                        if(!saved.equals("")) {
                            SharedPrefManager.getInstance(getContext()).writeString("email", emailText);
                        }
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.edit_password:
                    if (!SignUpActivity.isValidPassword(editText.getText().toString())) {
                        editText.setText(passwordText);
                        Toast.makeText(getContext(), "Password Should Contain 6-12 Characters with capital & small letters and numbers", Toast.LENGTH_LONG).show();
                    }else {
                        passwordText = password.getText().toString();
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                    break;
            }

            UserDto userDto = MainScreenActivity.currentUser;
            userDto.setFirstName(fName);
            userDto.setLastName(lName);
            userDto.setEmail(emailText);
            userDto.setPassword(passwordText);
            userDto.setNickName(String.format("%s %s", fName, lName));

            int update = userService.updateUser(userDto);
            if(update != 0) {
                MainScreenActivity.setCurrentUser(userDto);
            } else {
                Toast.makeText(getContext(), "Updated failed", Toast.LENGTH_LONG).show();
            }
        }

        editText.setEnabled(!editText.isEnabled());
    }

}