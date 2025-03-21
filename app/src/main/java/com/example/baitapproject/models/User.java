package com.example.baitapproject.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userId")
    private int userId;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("fullname")
    private String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
