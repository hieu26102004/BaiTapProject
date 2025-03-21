package com.example.baitapproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baitapproject.dto.LoginRequest;
import com.example.baitapproject.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    //22110373 - Nguyen Van Luan
    ImageButton btn_login;
    TextView etUsername;
    TextView etPassword;
    CheckBox cbRememberMe;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Kiểm tra xem đã lưu username trong SharedPreferences chưa
        SharedPreferences preferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        String savedUsername = preferences.getString("USERNAME", null);
        if (savedUsername != null) {
            // Nếu đã lưu, chuyển sang MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Ánh xạ view
        etUsername = findViewById(R.id.editTextName);
        etPassword = findViewById(R.id.editTextPassword);
        tvRegister = findViewById(R.id.textRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login = findViewById(R.id.imageButton);
        cbRememberMe = findViewById(R.id.cbmemberme);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        LoginRequest loginRequest = new LoginRequest(username, password);

        // Gọi API đăng nhập
        Call<User> call = apiService.login(loginRequest);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (cbRememberMe.isChecked()) {
                        SharedPreferences preferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("USERNAME", username);
                        editor.putString("FULLNAME", response.body().getFullname());
                        editor.apply();
                    }
                    else {

                    }
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển sang MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", etUsername.getText().toString().trim());
                    startActivity(intent);
                    finish();
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
}
