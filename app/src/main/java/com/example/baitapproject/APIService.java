package com.example.baitapproject;

import com.example.baitapproject.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @POST("/auth/login")
    Call<User> login(@Body LoginRequest request);
}
