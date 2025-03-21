package com.example.baitapproject;


import retrofit2.Call;

import com.example.baitapproject.dto.ApiResponse;
import com.example.baitapproject.dto.OtpRequest;
import com.example.baitapproject.dto.RegisterRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    @POST("auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);
    @POST("auth/send-activation-otp")
    Call<ApiResponse> sendActivationOtp(@Body RegisterRequest request);
    @POST("auth/activate-account")
    Call<ApiResponse> activateAccount(@Body OtpRequest request);
}
