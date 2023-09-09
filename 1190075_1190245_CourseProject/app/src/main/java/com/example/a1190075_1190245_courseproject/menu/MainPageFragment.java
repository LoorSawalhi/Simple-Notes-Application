package com.example.a1190075_1190245_courseproject.menu;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.NoteFragment;
import com.example.a1190075_1190245_courseproject.NoteLayoutFragment;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.adapter.NewNoteAdapter;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.List;

import javax.inject.Inject;



public class MainPageFragment extends Fragment implements NewNoteAdapter.noteOnClickListener {

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
    private AlertDialog.Builder builder;

    public MainPageFragment() {

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

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_page, container, false);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        ImageView addIcon = rootView.findViewById(R.id.add_icon);
        title = rootView.findViewById(R.id.fill_title);
        content = rootView.findViewById(R.id.fill_content);

        noteItems = noteService.listUserNotes(MainScreenActivity.currentUser.getId());

        recyclerView = rootView.findViewById(R.id.notes_grid);
        adapter = new NewNoteAdapter(noteItems, getContext());

        adapter.setOnNoteItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        recyclerView.setAdapter(adapter);

        addIcon.setOnClickListener(v -> {
                if(checkFields()){
                    NoteDto noteDto = new NoteDto();
                    noteDto.setTitle(title.getText().toString());
                    noteDto.setContent(content.getText().toString());
                    noteDto.setUserId(MainScreenActivity.currentUser.getId());

                    int add = noteService.addNote(noteDto);
                    if(add != -1) {
                        noteItems.add(noteDto);
                        Toast.makeText(getContext(), "Note Added Successfully", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Failed adding the note", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();

                }
                title.setText("");
                content.setText("");
            });

        return rootView;
    }

    @Override
    public void openNote(NoteDto noteDto) {
        Fragment newFragment = new NoteLayoutFragment(noteDto, new MainPageFragment());
        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean checkFields() {
        return !title.getText().toString().isBlank() && !content.getText().toString().isBlank();
    }

    public void updateList(List<NoteDto> filteredList) {
        noteItems.clear();
        noteItems.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }


}
