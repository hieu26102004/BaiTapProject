package com.example.baitapproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.baitapproject.dto.ApiResponse;
import com.example.baitapproject.dto.RegisterRequest;

import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    //Phạm Danh Hưởng - 22110344
    ImageButton btnSignUp;
    EditText etPassword, etFullName, etPhone, etEmail, eUsername;
    APIService apiService;
    RetrofitClient retrofitClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AnhXa();

        retrofitClient.getRetrofit();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        btnSignUp.setOnClickListener(v -> {
            String username = eUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String fullName = etFullName.getText().toString().trim();
            String phoneNumber = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            RegisterRequest request = new RegisterRequest(fullName, phoneNumber, email, username, password);
            registerUser(request);

        });
    }
    //Chuyển qua activity otp_register
    public void openOtpRegisterActivity(String email) {

        Intent intent = new Intent(this, OtpRegisterActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);


    }
    private void AnhXa() {
        etPassword = findViewById(R.id.editTextPassword);
        etFullName = findViewById(R.id.editTextFullname);
        etPhone = findViewById(R.id.editTextPhone);
        etEmail = findViewById(R.id.editTextEmail);
        eUsername = findViewById(R.id.editTextUsername);
        btnSignUp = findViewById(R.id.btnSignUp);
    }
    private void registerUser(RegisterRequest request) {
        Call<ApiResponse> call = apiService.registerUser(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        sendOtp(request);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Lỗi: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("Register", "Error: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Gửi OTP
    private void sendOtp(RegisterRequest request) {
        Call<ApiResponse> otpCall = apiService.sendActivationOtp(request);
        otpCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(RegisterActivity.this, "OTP đã được gửi!", Toast.LENGTH_SHORT).show();
                        openOtpRegisterActivity(request.getEmail()); // Mở activity OTP
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Lỗi gửi OTP: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Lỗi kết nối OTP!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("SendOtp", "Error: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Lỗi hệ thống khi gửi OTP!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
