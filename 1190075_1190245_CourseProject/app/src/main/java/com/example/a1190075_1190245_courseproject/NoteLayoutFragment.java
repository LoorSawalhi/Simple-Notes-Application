package com.example.a1190075_1190245_courseproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;


public class NoteLayoutFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageView edit;
    private ImageView delete;
    private ImageView share;
    private ImageView fav;
    private TextView title;
    private TextView date;
    private TextView content;
    private NoteDto noteItem;

    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;

    private AlertDialog.Builder builder;

    public NoteLayoutFragment() {
    }

    public NoteLayoutFragment(NoteDto noteDto) {
        this.noteItem = noteDto;
    }

    public static NoteLayoutFragment newInstance(String param1, String param2) {
        NoteLayoutFragment fragment = new NoteLayoutFragment();
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
        View itemView = inflater.inflate(R.layout.fragment_note_layout, container, false);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        edit = itemView.findViewById(R.id.edit);
        delete = itemView.findViewById(R.id.delete);
        share = itemView.findViewById(R.id.share);
        fav = itemView.findViewById(R.id.favourite);
        title = itemView.findViewById(R.id.display_title);
        content = itemView.findViewById(R.id.display_content);
        date = itemView.findViewById(R.id.display_date);

        title.setText(noteItem.getTitle());
        content.setText(noteItem.getContent());
        date.setText(noteItem.getCreatedOn());


        System.out.println(MainScreenActivity.currentUser.getId() + noteItem.getId());

        TransitionDrawable transitionDrawable = (TransitionDrawable) fav.getDrawable();

        AtomicBoolean flag = new AtomicBoolean(false);


        if(noteItem.isFavourite()) {
            transitionDrawable.reverseTransition(1);
            flag.set(true);
        }

        edit.setOnClickListener(v -> {
            Fragment newFragment = new NoteFragment(noteItem, this);
            FragmentTransaction transaction = requireFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        delete.setOnClickListener(v -> {
            builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Alert")
                    .setMessage("Delete This Note! It Won't Be Restored")
                    .setCancelable(true)
                    .setPositiveButton("YES", (dialog, which) ->{
                        int deleted = noteService.deleteNote(noteItem.getId());

                        if(deleted != 0) {
                            Toast.makeText(getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Deleting Failed", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton("NO", (dialog, which) -> dialog.cancel()).show();
        });

        share.setOnClickListener(v -> {
            Intent gmailIntent =new Intent();
            gmailIntent.setAction(Intent.ACTION_SENDTO);
            gmailIntent.setDataAndType(Uri.parse("mailto:"), "message/rfc822");
            gmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{MainScreenActivity.currentUser.getEmail()});
            gmailIntent.putExtra(Intent.EXTRA_SUBJECT,title.getText().toString());
            gmailIntent.putExtra(Intent.EXTRA_TEXT, content.getText().toString());
            startActivity(gmailIntent);
        });

        fav.setOnClickListener(v -> {

            if (!flag.get()) {
                transitionDrawable.reverseTransition(500);
                int setFave = noteService.setFavourite(MainScreenActivity.currentUser.getId(), noteItem.getId());
                if(setFave != 0) {
                    noteItem.setFavourite(true);
                    flag.set(true);
                }
            } else {
                transitionDrawable.reverseTransition(500);
                int delFave = noteService.deleteFavourite(MainScreenActivity.currentUser.getId(), noteItem.getId());
                if(delFave != 0) {
                    noteItem.setFavourite(false);

                    flag.set(false);
                }
            }
        });

        return itemView;
    }
}