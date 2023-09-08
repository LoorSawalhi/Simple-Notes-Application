package com.example.a1190075_1190245_courseproject.menu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.NoteLayoutFragment;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.adapter.NewNoteAdapter;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.enums.Preference;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.List;

import javax.inject.Inject;

public class SortingFragment extends Fragment implements NewNoteAdapter.noteOnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Inject
    public UserServiceImpl userService;
    @Inject
    public NoteServiceImpl noteService;
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private NewNoteAdapter adapter;
    private List<NoteDto> noteItems;
    private AlertDialog.Builder builder;

    public SortingFragment() {
    }

    public static SortingFragment newInstance(String param1, String param2) {
        SortingFragment fragment = new SortingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sorting, container, false);

        String[] options = {"CREATION DATE", "ALPHABETICALLY"};
        final Spinner sortingSpinner = view.findViewById(R.id.sort_spinner);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, options);
        sortingSpinner.setAdapter(objGenderArr);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        String userId = MainScreenActivity.currentUser.getId();

        if (MainScreenActivity.currentUser.getPreference().equals(Preference.CREATIONDATE)) {
            noteItems = noteService.getSorted(userId, "creationDate DESC");
            sortingSpinner.setSelection(0);
        } else {
            noteItems = noteService.getSorted(userId, "title");
            sortingSpinner.setSelection(1);
        }

        recyclerView = view.findViewById(R.id.fav_grid);
        adapter = new NewNoteAdapter(noteItems, getContext());

        adapter.setOnNoteItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        recyclerView.setAdapter(adapter);

        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (sortingSpinner.getSelectedItem().equals("CREATION DATE")) {
                    noteItems.clear();
                    noteItems.addAll(noteService.getSorted(userId, "creationDate DESC"));
                    MainScreenActivity.currentUser.setPreference(Preference.CREATIONDATE);
                } else {
                    noteItems.clear();
                    noteItems.addAll(noteService.getSorted(userId, "title"));
                    MainScreenActivity.currentUser.setPreference(Preference.ALPHABETICALLY);

                }
                int update = userService.updateUser(MainScreenActivity.currentUser);
                if (update != 0) {
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        return view;
    }

    @Override
    public void openNote(NoteDto noteDto) {
        Fragment newFragment = new NoteLayoutFragment(noteDto);
        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}