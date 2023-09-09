package com.example.a1190075_1190245_courseproject.menu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;
    private NewNoteAdapter noteAdapter;
    private CategoryAdapter categoryAdapter;
    private List<TagDto> tags;
    static List<NoteDto> noteItems;
    static TagDto currentTag = new TagDto();

    public CategorisationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorisation, container, false);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        noteItems = noteService.listUserNotes(MainScreenActivity.currentUser.getId());
        tags = noteService.getAllTagsForUser(MainScreenActivity.currentUser.getId());

        currentTag.setLabel("All");

        RecyclerView categories = view.findViewById(R.id.categories_grid);
        RecyclerView notes = view.findViewById(R.id.categorized_notes);
        Button add = view.findViewById(R.id.add_tag);

        add.setOnClickListener(v -> showPopupDialog());

        noteAdapter = new NewNoteAdapter(noteItems, requireContext());
        noteAdapter.setOnNoteItemClickListener(this);

        categoryAdapter = new CategoryAdapter(tags, requireContext());

        notes.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        notes.setAdapter(noteAdapter);

        categories.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categories.setAdapter(categoryAdapter);
        categoryAdapter.setOnTagClickListener(this);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void updateList(TagDto tag) {
        noteItems.clear();
        List<NoteDto> notes;

        currentTag.setLabel(tag.getLabel());

        if(tag.getLabel().equals("All")) {
            notes = noteService.listUserNotes(MainScreenActivity.currentUser.getId());
        } else {
            notes = noteService.getNotesByTagLabel(tag.getLabel(), MainScreenActivity.currentUser.getId());
        }

        noteItems.addAll(notes);
        noteAdapter.notifyDataSetChanged();
    }

    /** @noinspection deprecation*/
    @Override
    public void openNote(NoteDto noteDto) {
        Fragment newFragment = new NoteLayoutFragment(noteDto, new CategorisationFragment());
        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void showPopupDialog() {

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.add_tag);

        EditText editText = dialog.findViewById(R.id.editTag);
        Button closeButton = dialog.findViewById(R.id.add_button);
        closeButton.setOnClickListener(view -> {
            String enteredText = editText.getText().toString();
            boolean exists = noteService.getAllTagsForUser(MainScreenActivity.currentUser.getId()).stream().anyMatch(tag -> tag.getLabel().equalsIgnoreCase(enteredText));
            if(exists) {
                Toast.makeText(getContext(), "The tag already exists", Toast.LENGTH_LONG).show();
            } else {
                TagDto tagDto = new TagDto();
                tagDto.setLabel(enteredText);
                tagDto.setUserId(MainScreenActivity.currentUser.getId());
                int add = noteService.addTag(tagDto);
                if(add != 0) {
                    tags.add(tagDto);
                    categoryAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Failed to Add tag", Toast.LENGTH_LONG).show();
                }
            }
            dialog.dismiss();

        });
        dialog.show();
    }

    public List<NoteDto> getList(){
        if(currentTag.getLabel().equals("All")) {
            return noteService.listUserNotes(MainScreenActivity.currentUser.getId());
        } else {
            return noteService.getNotesByTagLabel(currentTag.getLabel(), MainScreenActivity.currentUser.getId());
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<NoteDto> filteredList) {
        noteItems.clear();
        noteItems.addAll(filteredList);
        noteAdapter.notifyDataSetChanged();
    }
}
