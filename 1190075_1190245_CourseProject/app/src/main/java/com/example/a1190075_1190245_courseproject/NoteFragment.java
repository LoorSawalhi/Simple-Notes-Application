package com.example.a1190075_1190245_courseproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


public class NoteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText date;
    private EditText title;
    private EditText content;
    private ImageView save;

    public NoteFragment() {
    }


    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
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
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        date = view.findViewById(R.id.note_date);
        content = view.findViewById(R.id.fill_c);
        title = view.findViewById(R.id.fill_t);
        save = view.findViewById(R.id.save_note);


        save.setOnClickListener(v -> {
            if(checkFields()){
                // edit note
            }
        });


        return view;
    }

    private boolean checkFields() {
        if(title.getText().toString().isBlank() || content.getText().toString().isBlank())
            return false;
        return true;
    }
}