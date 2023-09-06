package com.example.a1190075_1190245_courseproject.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.adapter.NewNoteAdapter;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.List;

import javax.inject.Inject;


public class FavouriteFragment extends Fragment {


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

    public FavouriteFragment() {
        // Required empty public constructor
    }


    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FavouriteFragment(List<NoteDto> notes) {
        this.noteItems = notes;
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
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        recyclerView = rootView.findViewById(R.id.fav_grid);
        adapter = new NewNoteAdapter(noteItems);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}