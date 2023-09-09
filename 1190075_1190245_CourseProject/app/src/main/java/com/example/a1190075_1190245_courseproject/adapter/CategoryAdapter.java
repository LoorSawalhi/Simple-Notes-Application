package com.example.a1190075_1190245_courseproject.adapter;

import static com.example.a1190075_1190245_courseproject.MainScreenActivity.colorArray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.R;
import com.example.a1190075_1190245_courseproject.dto.TagDto;
import com.example.a1190075_1190245_courseproject.service.impl.NoteServiceImpl;
import com.example.a1190075_1190245_courseproject.service.impl.UserServiceImpl;

import java.util.List;

import javax.inject.Inject;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<TagDto> tags;
    @Inject
    public UserServiceImpl userService;

    @Inject
    public NoteServiceImpl noteService;
    private CategoryAdapter.tagOnClickListener listener;


    public CategoryAdapter(List<TagDto> tags, Context context) {
        this.tags = tags;
        MyApplication myApplication = (MyApplication) context.getApplicationContext();
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
        TagDto tagItem = tags.get(position);
        holder.tag.setText(tagItem.getLabel());

        holder.tag.setOnClickListener(view -> listener.updateList(tagItem));

        int colorIndex = (position + colorArray.length + 4) % colorArray.length;
        holder.tag.setBackgroundColor(colorArray[colorIndex]);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void setOnTagClickListener(CategoryAdapter.tagOnClickListener listener) {
        this.listener = listener;
    }

    public interface tagOnClickListener {

        void updateList(TagDto tag);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag = itemView.findViewById(R.id.category);

        }
    }
}
