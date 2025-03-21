package com.example.baitapproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.baitapproject.dto.ApiResponse;
import com.example.baitapproject.dto.OtpRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpRegisterActivity extends AppCompatActivity {
    private ImageButton btnOtp;
    RetrofitClient retrofitClient;
    APIService apiService;
    //Phạm Danh Hưởng - 22110344
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_otp_register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnOtp = findViewById(R.id.btnOtp);
        retrofitClient.getRetrofit();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextOtp = findViewById(R.id.editTextOtp);
                String otp = editTextOtp.getText().toString().trim();


                String email = getIntent().getStringExtra("email");
                OtpRequest otpRequest = new OtpRequest(email, otp);
                activateAccount(otpRequest);
            }

        });
    }
    private void activateAccount(OtpRequest otpRequest) {
        Call<ApiResponse> call = apiService.activateAccount(otpRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(OtpRegisterActivity.this, "Kich hoạt tài khoản thành công!", Toast.LENGTH_SHORT).show();
                        openLoginActivity();
                    } else {
                        Toast.makeText(OtpRegisterActivity.this, "Lỗi: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OtpRegisterActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("Register", "Error: " + t.getMessage());
                Toast.makeText(OtpRegisterActivity.this, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openLoginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}
