// 221110297 - Pham Ngoc Duy

package com.example.baitapproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

public class HomeActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        databaseHandler = new DatabaseHandler(this);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loggedInUser = databaseHandler.getLoggedInUser();
                if (loggedInUser != null) {
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

//    private void InitDatabaseSQLite() {
//        // Khởi tạo database
//        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
//
//        // Tạo bảng Notes nếu chưa có
//        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS User(Id INTEGER PRIMARY KEY AUTOINCREMENT,  username VARCHAR(200), password TEXT, logged_in INTEGER DEFAULT 0)");
//    }
}
