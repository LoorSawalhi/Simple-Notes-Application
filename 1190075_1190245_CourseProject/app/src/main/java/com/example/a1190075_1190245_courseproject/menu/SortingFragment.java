package com.example.a1190075_1190245_courseproject.menu;

import android.annotation.SuppressLint;
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

    @Inject
    public UserServiceImpl userService;
    @Inject
    public NoteServiceImpl noteService;
    private NewNoteAdapter adapter;
    private static List<NoteDto> noteItems;

    public SortingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sorting, container, false);

        String[] options = {"CREATION DATE", "ALPHABETICALLY"};
        final Spinner sortingSpinner = view.findViewById(R.id.sort_spinner);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, options);
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

        RecyclerView recyclerView = view.findViewById(R.id.fav_grid);
        adapter = new NewNoteAdapter(noteItems, requireContext());

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

    /** @noinspection deprecation*/
    @Override
    public void openNote(NoteDto noteDto) {
        Fragment newFragment = new NoteLayoutFragment(noteDto, new SortingFragment());
        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static List<NoteDto> getList(){
        return noteItems;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<NoteDto> filteredList) {
        noteItems.clear();
        noteItems.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }

}