package com.example.baitapproject;

import com.example.baitapproject.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("books/categories")
    Call<List<Category>> getCategoryAll();
}
