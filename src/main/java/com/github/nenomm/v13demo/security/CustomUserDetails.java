package com.github.nenomm.v13demo.security;


public class CustomUserDetails {
    Long userId;

    public CustomUserDetails(Long p_userId) {
        this.userId = p_userId;
    }

    public Long getUserId() {
        return userId;
    }
}
