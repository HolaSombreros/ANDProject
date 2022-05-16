package com.group2.foodie.model;


public class Follower {
    private String id;
    private String username;
    private String email;
    private boolean isFollowed;

    public Follower() {

    }

    public Follower(String id, String username, String email, boolean isFollowed) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isFollowed = isFollowed;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follower follower = (Follower) o;
        return this.id.equals(follower.id);
    }

}
