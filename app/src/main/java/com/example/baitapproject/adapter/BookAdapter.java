package com.example.baitapproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baitapproject.R;
import com.example.baitapproject.models.Book;
import com.example.baitapproject.models.Category;

import java.util.List;

public class BookAdapter extends BaseAdapter {
    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_books, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.image);
            holder.textView = convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu sách
        Book book = bookList.get(position);
        holder.textView.setText(book.getTitle());

        // Load ảnh bằng Glide (nếu có URL ảnh)
        Glide.with(context)
                .load(book.getCoverUrl()) // Giả sử `imageUrl` là đường dẫn ảnh của sách
                .into(holder.imageView);

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}

