package com.example.a1190075_1190245_courseproject.adapter;

import static com.example.a1190075_1190245_courseproject.MainScreenActivity.colorArray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.dto.NoteDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.List;

import javax.inject.Inject;


public class NewNoteAdapter extends RecyclerView.Adapter<NewNoteAdapter.ViewHolder> {

    private final List<NoteDto> noteItems;
    @Inject
    public UserServiceImpl userService;
    @Inject
    public NoteServiceImpl noteService;
    private noteOnClickListener listener;

    public NewNoteAdapter(List<NoteDto> noteItems, Context context) {
        this.noteItems = noteItems;
        MyApplication myApplication = (MyApplication) context.getApplicationContext();
        myApplication.getAppComponent().inject(this);

    }

    @Override
    public int getItemCount() {
        return noteItems.size();
    }

    public void setOnNoteItemClickListener(noteOnClickListener listener) {
        this.listener = listener;
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
        holder.dateTextView.setText(noteItem.getCreatedOn());
        holder.titleTextView.setText(noteItem.getTitle());

        holder.item.setOnClickListener(view -> {
            listener.openNote(noteItem);
        });

        int colorIndex = position % colorArray.length;
        holder.card.setBackgroundColor(colorArray[colorIndex]);
    }

    public interface noteOnClickListener {

        void openNote(NoteDto noteDto);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView dateTextView;
        private final ConstraintLayout card;

        private final CardView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            dateTextView = itemView.findViewById(R.id.create_date);
            item = itemView.findViewById(R.id.cardView);
            card = itemView.findViewById(R.id.back_item);
        }
    }
}
