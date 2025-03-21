package com.example.baitapproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baitapproject.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    //22110373 - Nguyen Van Luan
    ImageButton btn_login;
    TextView etUsername;
    TextView etPassword;
    TextView tvRegister;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        dbHandler = new DatabaseHandler(this);
        String loggedInUser = dbHandler.getLoggedInUser();
        if (loggedInUser != null) {
            openMainActivity();
        }

        tvRegister = findViewById(R.id.textRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login = findViewById(R.id.imageButton);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();

            }
        });
    }
    private void loginUser() {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

        etUsername = findViewById(R.id.editTextName);
        etPassword = findViewById(R.id.editTextPassword);
        // Giả sử bạn có EditText cho username và password
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        LoginRequest loginRequest = new LoginRequest(username, password);

        Call<User> call = apiService.login(loginRequest);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lưu user vào SQLite
                    User user = response.body();

                    dbHandler.QueryData("INSERT INTO User (username, Guest, password, logged_in) " +
                            "VALUES ('" + user.getUsername() + "', '" + user.getFullname() + "', '" + user.getPassword() + "', 1)");

                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    openMainActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
