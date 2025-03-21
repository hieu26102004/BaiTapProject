package com.example.baitapproject.dto;

public class RegisterRequest {
    private String full_name;
    private String phone_number;
    private String email;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RegisterRequest() {
    }

    public RegisterRequest(String full_name, String phone_number, String email, String username, String password) {
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}