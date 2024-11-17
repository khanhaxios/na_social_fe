package com.kt.na_social.model;

public class User {
    private String uid;
    private String email;
    private String username;
    private String idToken;
    private String password;

    private String profileAvatar;

    private User(Builder builder) {
        this.email = builder.email;
        this.username = builder.username;
        this.idToken = builder.idToken;
        this.password = builder.password;
    }

    public static class Builder {
        private String email;
        private String username;
        private String idToken;
        private String password;
        private String profileAvatar;
        private String uid;


        // Builder methods to set values
        public Builder setEmail(String email) {
            this.email = email;
            return this; // Return the Builder object for method chaining
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this; // Return the Builder object for method chaining
        }

        public Builder setProfileAvatar(String avatar) {
            this.profileAvatar = avatar;
            return this;
        }

        public Builder setIdToken(String idToken) {
            this.idToken = idToken;
            return this; // Return the Builder object for method chaining
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this; // Return the Builder object for method chaining
        }

        public Builder setUid(String uid) {
            this.uid = uid;
            return this;
        }

        // Build method to create the User instance
        public User build() {
            return new User(this);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileAvatar() {
        return profileAvatar;
    }

    public void setProfileAvatar(String profileAvatar) {
        this.profileAvatar = profileAvatar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
