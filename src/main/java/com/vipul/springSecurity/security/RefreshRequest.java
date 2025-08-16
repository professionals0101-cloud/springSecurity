package com.vipul.springSecurity.security;

public class RefreshRequest {
    private String refreshToken;
    // getters & setters

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}