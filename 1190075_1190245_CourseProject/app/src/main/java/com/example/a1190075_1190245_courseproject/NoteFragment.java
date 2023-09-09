package com.example.a1190075_1190245_courseproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.service.NoteService;

import javax.inject.Inject;


public class NoteFragment extends Fragment {

    private EditText title;
    private EditText content;
    private NoteDto note;
    private Fragment fragment;

    @Inject
    NoteService noteService;
    public NoteFragment() {
    }

    public NoteFragment(NoteDto noteDao, Fragment targetFragment) {
        this.note = noteDao;
        this.fragment = targetFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /** @noinspection deprecation*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        TextView date = view.findViewById(R.id.note_date);
        content = view.findViewById(R.id.fill_c);
        title = view.findViewById(R.id.fill_t);
        ImageView save = view.findViewById(R.id.save_note);

        content.setText(note.getContent());
        title.setText(note.getTitle());
        date.setText(note.getCreatedOn());

        save.setOnClickListener(v -> {
            if(checkFields()){
                NoteDto noteDto = note;
                noteDto.setTitle(title.getText().toString());
                noteDto.setContent(content.getText().toString());

                int update = noteService.updateNote(noteDto.getId(), noteDto);

                if(update != 0) {
                    Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Empty Fields!", Toast.LENGTH_SHORT).show();
            }

            FragmentTransaction transaction = requireFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });


        return view;
    }

    private boolean checkFields() {
        return !title.getText().toString().isBlank() && !content.getText().toString().isBlank();
    }
}