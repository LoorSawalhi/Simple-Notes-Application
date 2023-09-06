package com.example.a1190075_1190245_courseproject.menu;

import static dagger.hilt.android.internal.Contexts.getApplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.adapter.NewNoteAdapter;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainPageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText title;
    private EditText content;
    private RecyclerView recyclerView;
    private NewNoteAdapter adapter;
    private List<NoteDto> noteItems;

    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;

    public MainPageFragment() {

    }

    public MainPageFragment(List<NoteDto> notes) {
        this.noteItems = notes;
    }

    public static MainPageFragment newInstance(String param1, String param2) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((MainScreenActivity) getActivity().getApplication()).getYourComponent().inject(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_page, container, false);

        ImageView addIcon = rootView.findViewById(R.id.add_icon);
        title = rootView.findViewById(R.id.fill_title);
        content = rootView.findViewById(R.id.fill_content);

        recyclerView = rootView.findViewById(R.id.notes_grid);
        adapter = new NewNoteAdapter(noteItems);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        recyclerView.setAdapter(adapter);

        addIcon.setOnClickListener(v -> {
                if(checkFields()){
                    // add note
                }
            });

        // Load your data into the noteItems list and call notifyDataSetChanged() on the adapter
        // when you want to refresh the data in the RecyclerView.

//        Note newItem = new Note("New Title", "New Content");
//        noteItems.add(newItem);
//        adapter.notifyDataSetChanged();

        return rootView;
    }

    private boolean checkFields() {
        if(title.getText().toString().isBlank() || content.getText().toString().isBlank())
            return false;
        return true;
    }
}