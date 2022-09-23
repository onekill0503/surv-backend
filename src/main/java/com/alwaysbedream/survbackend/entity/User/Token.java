package com.alwaysbedream.survbackend.entity.User;

public class Token {
    private String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Token(String token) {
        this.token = token;
    }
}
