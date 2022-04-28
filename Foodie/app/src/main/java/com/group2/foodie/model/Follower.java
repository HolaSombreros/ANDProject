package com.group2.foodie.model;

public class Follower {
    private String id;
    private String username;
    private String email;
    private boolean isFollowed;

    public Follower() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollows(boolean follows) {
        isFollowed = follows;
    }
}
