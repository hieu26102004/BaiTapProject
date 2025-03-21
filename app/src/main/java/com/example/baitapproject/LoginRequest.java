package com.example.baitapproject;

public class LoginRequest {
    private String username;
    private String password;

    // Constructor mặc định (cần thiết cho các thư viện như Gson)
    public LoginRequest() {
    }

    // Constructor có tham số
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter và Setter cho username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter và Setter cho password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
