package com.example.applogin.bean;

public class User {
    private String userId;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "userid='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
