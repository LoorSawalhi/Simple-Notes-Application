package com.example.a1190075_1190245_courseproject.menu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.NoteLayoutFragment;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.adapter.CategoryAdapter;
import com.example.a1190075_1190245_courseproject.adapter.NewNoteAdapter;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.dto.TagDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.List;

import javax.inject.Inject;

public class CategorisationFragment extends Fragment implements CategoryAdapter.tagOnClickListener, NewNoteAdapter.noteOnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;
    private AlertDialog.Builder builder;
    private NewNoteAdapter noteAdapter;
    List<NoteDto> noteItems;
    public CategorisationFragment() {
        // Required empty public constructor
    }

    public static CategorisationFragment newInstance(String param1, String param2) {
        CategorisationFragment fragment = new CategorisationFragment();
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

    //TODO: add category to note, add none default :)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorisation, container, false);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        noteItems = noteService.listUserNotes(MainScreenActivity.currentUser.getId());
        List<TagDto> tags = noteService.getAllTagsForUser(MainScreenActivity.currentUser.getId());

        RecyclerView categories = view.findViewById(R.id.categories_grid);
        RecyclerView notes = view.findViewById(R.id.categorized_notes);
        Button add = view.findViewById(R.id.add_tag);

        add.setOnClickListener(v -> {
//            builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("New Tag")
//                    .setMessage("Delete This Note! It Won't Be Restored")
//                    .setCancelable(true)
//                    .setPositiveButton("YES", (dialog, which) ->{
//
//                        Toast.makeText(getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
//                    })
//                    .setNegativeButton("NO", (dialog, which) -> dialog.cancel()).show();
        });

        noteAdapter = new NewNoteAdapter(noteItems, getContext());
        noteAdapter.setOnNoteItemClickListener(this);

        CategoryAdapter categoryAdapter = new CategoryAdapter(tags, getContext());

        notes.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        notes.setAdapter(noteAdapter);

        categories.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        categories.setAdapter(categoryAdapter);
        categoryAdapter.setOnTagClickListener(this);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void updateList(TagDto tag) {
        noteItems = noteService.getNotesByTagLabel(tag.getLabel(), MainScreenActivity.currentUser.getId());
        noteItems.forEach(n -> System.out.println(n.toString()));
        noteAdapter.notifyDataSetChanged();
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