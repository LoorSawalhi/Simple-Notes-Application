package com.example.a1190075_1190245_courseproject.adapter;

import static com.example.a1190075_1190245_courseproject.MainScreenActivity.colorArray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.dto.TagDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private MyApplication myApplication;
    private Context context;

    private CategoryAdapter.tagOnClickListener listener;
    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;

    private List<TagDto> tagDtos = new ArrayList<>();


    public CategoryAdapter(List<TagDto> tagDtos, Context context) {
        this.tagDtos = tagDtos;
        this.context = context;
        this.myApplication = (MyApplication) context.getApplicationContext();
        myApplication.getAppComponent().inject(this);
    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);


        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        TagDto tagItem = tagDtos.get(position);
        holder.tag.setText(tagItem.getLabel());

        holder.tag.setOnClickListener(view -> {
            listener.updateList(tagItem);
        });

        int colorIndex = (position + colorArray.length + 4) % colorArray.length;
        holder.tag.setBackgroundColor(colorArray[colorIndex]);
    }

    @Override
    public int getItemCount() {
        return tagDtos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tag;
        private CardView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag = itemView.findViewById(R.id.category);
            item = itemView.findViewById(R.id.card_c);

        }
    }

    public void setOnTagClickListener(CategoryAdapter.tagOnClickListener listener) {
        this.listener = listener;
    }

    public interface tagOnClickListener {

        void updateList(TagDto tag);
    }
}
