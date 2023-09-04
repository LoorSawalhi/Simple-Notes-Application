package com.example.a1190075_1190245_courseproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.dto.Note;

import java.util.List;

public class NewNoteAdapter extends RecyclerView.Adapter<NewNoteAdapter.ViewHolder> {

private List<Note> noteItems;

public NewNoteAdapter(List<Note> noteItems) {
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
        Note noteItem = noteItems.get(position);
//        holder.titleTextView.setText(noteItem.getTitle());
//        holder.contentTextView.setText(noteItem.getContent());
        }

@Override
public int getItemCount() {
        return noteItems.size();
        }

public static class ViewHolder extends RecyclerView.ViewHolder {
    TextView titleTextView;
    TextView contentTextView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title);
        contentTextView = itemView.findViewById(R.id.note_content);
    }
}
}