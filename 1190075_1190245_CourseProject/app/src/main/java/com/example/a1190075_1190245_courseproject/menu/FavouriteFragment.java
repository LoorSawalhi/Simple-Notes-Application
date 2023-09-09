package com.example.a1190075_1190245_courseproject.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.List;

import javax.inject.Inject;


public class FavouriteFragment extends Fragment implements NewNoteAdapter.noteOnClickListener {


    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;
    private List<NoteDto> noteItems;
    private NewNoteAdapter adapter;

    public FavouriteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);


        noteItems = userService.getUserFavouriteNotes(MainScreenActivity.currentUser.getId());

        RecyclerView recyclerView = rootView.findViewById(R.id.fav_grid);
        adapter = new NewNoteAdapter(noteItems, requireContext());

        adapter.setOnNoteItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        recyclerView.setAdapter(adapter);
        return rootView;
    }


    /** @noinspection deprecation*/
    @Override
    public void openNote(NoteDto noteDto) {
        Fragment newFragment = new NoteLayoutFragment(noteDto, new FavouriteFragment());
        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<NoteDto> filteredList) {
        noteItems.clear();
        noteItems.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }

}