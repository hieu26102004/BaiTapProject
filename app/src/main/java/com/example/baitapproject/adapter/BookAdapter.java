package com.example.baitapproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baitapproject.R;
import com.example.baitapproject.models.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_books, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textView.setText(book.getTitle());

        Glide.with(context)
                .load(book.getCoverUrl())
                .placeholder(R.drawable.loading) // Ảnh tạm thời khi loading
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void addBooks(List<Book> newBooks) {
        bookList.addAll(newBooks);
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.title);
        }
    }
}
