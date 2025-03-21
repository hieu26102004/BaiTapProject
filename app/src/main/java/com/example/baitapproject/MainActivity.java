package com.example.baitapproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitapproject.adapter.BookAdapter;
import com.example.baitapproject.adapter.CategoryAdapter;
import com.example.baitapproject.models.Book;
import com.example.baitapproject.models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler db;
    private TextView userNameTextView;
    private TextView userIdTextView;

    RecyclerView rcCate;
    
    RecyclerView rcBook;
    CategoryAdapter categoryAdapter;

    BookAdapter bookAdapter;
    APIService apiService;
    List<Category> categoryList;

    List<Book> bookList;
    private boolean isLoading = false;
    private List<Book> displayedBooks = new ArrayList<>();

    private int currentPage = 0;
    private final int pageSize = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView userAvatarImageView = findViewById(R.id.imageViewLogout);
        userAvatarImageView.setOnClickListener(v -> logout());

        userNameTextView = findViewById(R.id.fullname);
        userIdTextView = findViewById(R.id.user_id);
        SharedPreferences preferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        String savedUsername = preferences.getString("USERNAME", null);

        if (savedUsername != null && !savedUsername.isEmpty()) {
            getUserInfo(savedUsername);
        } else {
            savedUsername = getIntent().getStringExtra("username");
            getUserInfo(savedUsername);
        }
        AnhXaCategory();
        GetCategory();

        AnhXaBook();
        GetBooks("Programming");
    }
    private void AnhXaCategory(){
        rcCate = (RecyclerView) findViewById(R.id.rc_category);
    }

    private void AnhXaBook(){
        rcBook = findViewById(R.id.recyclerViewBook);
    }

    private void GetCategory() {
        // Gọi Interface trong APIService
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body(); // Nhận mảng

                    // Khởi tạo Adapter
                    categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                            getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                    );
                    rcCate.setLayoutManager(layoutManager);
                    rcCate.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                    GetBooks("Programming");
                } else {
                    int statusCode = response.code();
                    // Handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("Logg", t.getMessage());
                Toast.makeText(MainActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void GetBooks(String categoryName) {
        TextView txtCategory = (TextView) findViewById(R.id.txtCategory);
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        apiService.getBookByCategory(categoryName).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookList = response.body(); // Nhận danh sách sách
                    txtCategory.setText(categoryName);

                    rcBook.setAdapter(bookAdapter);
                    displayedBooks.clear();
                    int initialLoadSize = Math.min(3, bookList.size());
                    displayedBooks.addAll(bookList.subList(0, initialLoadSize));




                    bookAdapter = new BookAdapter(MainActivity.this, displayedBooks);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
                    rcBook.setLayoutManager(gridLayoutManager);
                    rcBook.setAdapter(bookAdapter);

                    initScrollListener();

                } else {
                    Toast.makeText(MainActivity.this, "Lỗi: Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {
                Log.e("API_ERROR", "Lỗi kết nối: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initScrollListener() {
        rcBook.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rcBook, int dx, int dy) {
                super.onScrolled(rcBook, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) rcBook.getLayoutManager();

                if (!isLoading && layoutManager != null &&
                        layoutManager.findLastCompletelyVisibleItemPosition() == displayedBooks.size() - 1) {
                    if (displayedBooks.size() < bookList.size()) {
                        loadMore();
                    }
                }
            }
        });
    }


    private void loadMore() {
        if (displayedBooks.size() >= bookList.size()) {
            isLoading = false;
            return;
        }

        isLoading = true;
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            int start = displayedBooks.size();
            int end = Math.min(start + 6, bookList.size());

            if (start < end) {
                displayedBooks.addAll(bookList.subList(start, end));
                bookAdapter.notifyItemRangeInserted(start, end - start);
            }

            if (displayedBooks.size() >= bookList.size()) {
                Toast.makeText(MainActivity.this, "Hết sách rồi", Toast.LENGTH_SHORT).show();
            }
            isLoading = false;
        }, 1500);
    }




    private void logout() {
        SharedPreferences preferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("USERNAME");
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void getUserInfo(String username) {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        Call<Map<String, String>> call = apiService.getUserInfo(username);

        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String fullName = response.body().get("full_name");
                    String userId = response.body().get("id");
                    userNameTextView.setText("Hi! " + fullName);
                    userIdTextView.setText("ID: " + userId);
                } else {
                    userNameTextView.setText("Hi! Guest");
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                userNameTextView.setText("Hi! Guest");
                Toast.makeText(MainActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}