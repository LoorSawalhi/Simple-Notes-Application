package com.example.a1190075_1190245_courseproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;

import java.util.List;

public class NewNoteAdapter extends RecyclerView.Adapter<NewNoteAdapter.ViewHolder> {

    private List<NoteDto> noteItems;

    public NewNoteAdapter(List<NoteDto> noteItems) {
            this.noteItems = noteItems;
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

        holder.edit.setOnClickListener(v -> {

        });

        holder.delete.setOnClickListener(v -> {

        });

        holder.share.setOnClickListener(v -> {

        });

        holder.fav.setOnClickListener(v -> {

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