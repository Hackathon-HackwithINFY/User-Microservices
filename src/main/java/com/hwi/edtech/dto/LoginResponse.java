package com.hwi.edtech.dto;

public class LoginResponse {
    private String token;
    private long expiresIn;
    private String email;

    // Getters
    public String getToken() {
        return token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public LoginResponse setEmail(String email) {
        this.email = email;
        return this;
    }
}
