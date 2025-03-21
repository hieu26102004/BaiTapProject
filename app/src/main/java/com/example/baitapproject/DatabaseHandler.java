package com.example.baitapproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "User";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLLUMN_FULLNAME = "Guest";
    private static final String COLUMN_LOGGED_IN = "logged_in";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void QueryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    // Truy vấn có trả kết quả select
    public Cursor GetData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT UNIQUE, " +
                COLLUMN_FULLNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_LOGGED_IN + " INTEGER DEFAULT 0)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }


    // Lưu trạng thái đăng nhập
    public void setLoggedIn(String username, boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGGED_IN, status ? 1 : 0);
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", new String[]{username});
    }

    // Kiểm tra xem có user nào đang đăng nhập không
    public String getLoggedInUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USERNAME + " FROM " + TABLE_USER +
                " WHERE " + COLUMN_LOGGED_IN + " = 1", null);

        if (cursor.moveToFirst()) {
            String username = cursor.getString(0);
            cursor.close();
            return username;
        }
        cursor.close();
        return null;
    }
    // Lấy tên đầy đủ của người dùng đang đăng nhập
    public String getLoggedInUserFullName() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLLUMN_FULLNAME + " FROM " + TABLE_USER +
                " WHERE " + COLUMN_LOGGED_IN + " = 1", null);

        if (cursor.moveToFirst()) {
            String fullName = cursor.getString(0);
            cursor.close();
            return fullName;
        }
        cursor.close();
        return null;
    }
    public void addUser(String username, String fullName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLLUMN_FULLNAME, fullName);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_LOGGED_IN, 1); // Đánh dấu đã đăng nhập

        db.insert(TABLE_USER, null, values);
        db.close();
    }

}
