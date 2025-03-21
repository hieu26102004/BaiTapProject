package com.example.baitapproject;

import com.example.baitapproject.dto.LoginRequest;
import com.example.baitapproject.models.Book;
import com.example.baitapproject.models.Category;
import com.example.baitapproject.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


import com.example.baitapproject.dto.ApiResponse;
import com.example.baitapproject.dto.OtpRequest;
import com.example.baitapproject.dto.RegisterRequest;

import java.util.List;
import java.util.Map;

import retrofit2.http.Path;

public interface APIService {
    @GET("books/categories")
    Call<List<Category>> getCategoryAll();

    @POST("auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);
    @POST("auth/send-activation-otp")
    Call<ApiResponse> sendActivationOtp(@Body RegisterRequest request);
    @POST("auth/activate-account")
    Call<ApiResponse> activateAccount(@Body OtpRequest request);
    @POST("/auth/login")
    Call<User> login(@Body LoginRequest request);
    @GET("books/categories/{bien}")
    Call<List<Book>> getBookByCategory(@Path("bien") String bien);
    @GET("/info/{username}")
    Call<Map<String, String>> getUserInfo(@Path("username") String username);
}
