package com.example.baitapproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baitapproject.R;
import com.example.baitapproject.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Category> array;

    public CategoryAdapter(Context context, List<Category> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = array.get(position);
        holder.genre.setText(category.getGenre());

        // Load ảnh với Glide
        Glide.with(context)
                .load(category.getUrl())
                .into(holder.images);


    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView genre;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.image_cate);
            genre = (TextView) itemView.findViewById(R.id.tvNameCategory);

            // Bắt sự kiện cho item holder trong MyViewHolder
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý khi nhấp vào 1 item category
                    Toast.makeText(context, "Bạn đã chọn category: "
                            + genre.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}