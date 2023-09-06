package com.example.a1190075_1190245_courseproject.adapter;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1190075_1190245_courseproject.MainScreenActivity;
import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.dto.FavouriteDto;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

public class NewNoteAdapter extends RecyclerView.Adapter<NewNoteAdapter.ViewHolder> {

    private List<NoteDto> noteItems = new ArrayList<>();

    private Context context;
    private MyApplication myApplication;

    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;
    private FavouriteDto favourite;

    public NewNoteAdapter(List<NoteDto> noteItems, Context context) {
        this.noteItems = noteItems;
        this.context = context;
        this.myApplication = (MyApplication) context.getApplicationContext();
        myApplication.getAppComponent().inject(this);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteDto noteItem = noteItems.get(position);
        holder.titleTextView.setText(noteItem.getTitle());
        holder.contentTextView.setText(noteItem.getContent());

        System.out.println(MainScreenActivity.currentUser.getId() + noteItem.getId());

        TransitionDrawable transitionDrawable = (TransitionDrawable)
                holder.fav.getDrawable();

        AtomicBoolean flag = new AtomicBoolean(false);
        if(userService.getFavourite(MainScreenActivity.currentUser.getId(), noteItem.getId()) != null) {
            transitionDrawable.reverseTransition(1);
            flag.set(true);
        }

        holder.edit.setOnClickListener(v -> {

        });

        holder.delete.setOnClickListener(v -> {

        });

        holder.share.setOnClickListener(v -> {

        });

        holder.fav.setOnClickListener(v -> {

            if (!flag.get()) {
                transitionDrawable.reverseTransition(500);
                userService.addFavourite(MainScreenActivity.currentUser.getId(), noteItem.getId());
                flag.set(true);
            } else {
                transitionDrawable.reverseTransition(500);
                userService.deleteFavourite(MainScreenActivity.currentUser.getId(), noteItem.getId());
                flag.set(false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView contentTextView;
        private ImageView edit;
        private ImageView delete;
        private ImageView share;
        private ImageView fav;

        private CardView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            contentTextView = itemView.findViewById(R.id.note_content);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            share = itemView.findViewById(R.id.share);
            fav = itemView.findViewById(R.id.favourite);
            contentTextView.setVisibility(View.INVISIBLE);
            item = itemView.findViewById(R.id.cardView);

            item.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    contentTextView.setVisibility(View.VISIBLE);
                }
            });

        }
    }
}