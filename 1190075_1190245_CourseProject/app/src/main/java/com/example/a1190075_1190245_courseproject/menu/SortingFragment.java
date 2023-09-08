package com.example.a1190075_1190245_courseproject.menu;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.NoteFragment;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.adapter.NewNoteAdapter;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.List;

import javax.inject.Inject;

public class SortingFragment extends Fragment implements NewNoteAdapter.noteOnClickListener {

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
    private AlertDialog.Builder builder;
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

        String[] options = { "CREATION_DATE", "ALPHABETICALLY"};
        final Spinner genderSpinner = view.findViewById(R.id.sort_spinner);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, options);
        genderSpinner.setAdapter(objGenderArr);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = (String) parentView.getItemAtPosition(position);
                if(genderSpinner.getSelectedItem().equals("CREATION_DATE")){

                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        recyclerView = view.findViewById(R.id.fav_grid);
        adapter = new NewNoteAdapter(noteItems, getContext());

        adapter.setOnNoteItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void deleteNoteOnClick(NoteDto noteDto) {

        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alert")
                .setMessage("Delete This Note! It Won't Be Restored")
                .setCancelable(true)
                .setPositiveButton("YES", (dialog, which) ->{
                    noteService.deleteNote(noteDto.getId());
                    noteItems.remove(noteDto);
                    Toast.makeText(getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("NO", (dialog, which) -> dialog.cancel()).show();
    }

    @Override
    public void editeNoteOnClick(NoteDto noteDto) {
        Fragment newFragment = new NoteFragment(noteDto, this);
        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}