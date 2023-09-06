package com.example.a1190075_1190245_courseproject.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.SignUpActivity;


public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
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

        firstName.setText("Loor");

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
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.edit_ln:
                    if (!SignUpActivity.isValidName(editText.getText().toString())) {
                        editText.setText(lName);
                        Toast.makeText(getContext(), "Names Should be 3-10 Alphabets", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.edit_email:
                    if (!SignUpActivity.isValidEmail(editText.getText().toString())) {
                        editText.setText(emailText);
                        Toast.makeText(getContext(), "Email Format Is Wrong", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.edit_password:
                    if (!SignUpActivity.isValidPassword(editText.getText().toString())) {
                        editText.setText(passwordText);
                        Toast.makeText(getContext(), "Password Should Contain 6-12 Characters with capital & small letters and numbers", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }

        editText.setEnabled(!editText.isEnabled());
    }

}