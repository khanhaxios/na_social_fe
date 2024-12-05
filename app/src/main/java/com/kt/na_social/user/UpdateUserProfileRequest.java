package com.kt.na_social.user;

public class UpdateUserProfileRequest {
    private String displayName;
    private String phoneNumber;
    private String email;

    // Constructor
    public UpdateUserProfileRequest(String displayName, String phoneNumber, String email) {
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
