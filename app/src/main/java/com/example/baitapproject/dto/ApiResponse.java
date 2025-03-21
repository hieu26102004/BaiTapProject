package com.example.baitapproject.dto;

public class ApiResponse {
    private boolean status;
    private String message;

    public boolean isSuccess() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
