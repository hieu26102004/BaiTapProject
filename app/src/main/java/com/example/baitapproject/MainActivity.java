package com.example.baitapproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitapproject.adapter.BookAdapter;
import com.example.baitapproject.adapter.CategoryAdapter;
import com.example.baitapproject.models.Book;
import com.example.baitapproject.models.Category;

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
    GridView gvBook;
    CategoryAdapter categoryAdapter;

    BookAdapter bookAdapter;
    APIService apiService;
    List<Category> categoryList;

    List<Book> bookList;
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
        userNameTextView = findViewById(R.id.fullname);
        userIdTextView = findViewById(R.id.user_id);
        SharedPreferences preferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        String savedUsername = preferences.getString("USERNAME", null);

        if (savedUsername != null && !savedUsername.isEmpty()) {
            getUserInfo(savedUsername);
        } else {
            userNameTextView.setText("Hi! Guest");
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
        gvBook = (GridView) findViewById(R.id.gvBook);
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
                    // Khởi tạo Adapter cho GridView
                    bookAdapter = new BookAdapter(MainActivity.this, bookList);

                    gvBook.setAdapter(bookAdapter);
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