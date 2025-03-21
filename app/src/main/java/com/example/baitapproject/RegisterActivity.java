package com.example.baitapproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    //Phạm Danh Hưởng - 22110344
    ImageButton btnSignUp;
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
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            openOtpRegisterActivity();
            finish();
        });
    }
    //Chuyển qua activity otp_register
    public void openOtpRegisterActivity() {
        startActivity(new Intent(this, OtpRegisterActivity.class));
    }

}
