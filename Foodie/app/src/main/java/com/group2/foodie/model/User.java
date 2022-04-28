package com.group2.foodie.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private List<Recipe> recipes;
    private List<Recipe> favoriteRecipes;
    private List<User> followers;
    private List<User> following;
    private List<Ingredient> ingredients;
    private Fridge fridge;
    private int profilePictureId;

    public User() {
        recipes = new ArrayList<>();
        favoriteRecipes = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    public User(String username, String email, String password, List<Recipe> recipes, List<Recipe> favoriteRecipes, List<User> followers, List<User> following, List<Ingredient> ingredients, Fridge fridge) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.recipes = recipes;
        this.favoriteRecipes = favoriteRecipes;
        this.followers = followers;
        this.following = following;
        this.ingredients = ingredients;
        this.fridge = fridge;
    }

    public User(String username, String email, String password, int profilePictureId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.recipes = new ArrayList<>();
        this.favoriteRecipes = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.fridge = new Fridge(ingredients);
        this.profilePictureId = 0;
    }
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.recipes = new ArrayList<>();
        this.favoriteRecipes = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.fridge = new Fridge(ingredients);
        profilePictureId = 0;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Fridge getFridge() {
        return fridge;
    }

    public void setFridge(Fridge fridge) {
        this.fridge = fridge;
    }

    public int getProfilePictureId() {
        return profilePictureId;
    }

    public void setProfilePictureId(int profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", recipes=" + recipes +
                ", favoriteRecipes=" + favoriteRecipes +
                ", followers=" + followers +
                ", following=" + following +
                ", ingredients=" + ingredients +
                ", fridge=" + fridge +
                ", profilePictureId=" + profilePictureId +
                '}';
    }
}