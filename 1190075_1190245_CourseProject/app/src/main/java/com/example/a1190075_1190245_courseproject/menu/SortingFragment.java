package com.example.a1190075_1190245_courseproject.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.adapter.NewNoteAdapter;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SortingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private NewNoteAdapter adapter;
    private List<NoteDto> noteItems;

    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;
    public SortingFragment() {
    }

    public SortingFragment(List<NoteDto> notes)  {
        this.noteItems = notes;
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

        String[] options = { "A-Z", "Z-A", "Date"};
        final Spinner genderSpinner = view.findViewById(R.id.sort_spinner);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, options);
        genderSpinner.setAdapter(objGenderArr);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        recyclerView = view.findViewById(R.id.fav_grid);
        adapter = new NewNoteAdapter(noteItems, getContext());

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        recyclerView.setAdapter(adapter);

        return view;
    }
}