package com.group2.foodie.repository;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.group2.foodie.livedata.RecipeListLiveData;
import com.group2.foodie.livedata.RecipeLiveData;
import com.group2.foodie.model.Recipe;

import java.io.ByteArrayOutputStream;

public class RecipeRepository {
    private static RecipeRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private RecipeListLiveData recipes;
    private RecipeLiveData recipe;
    private MutableLiveData<Boolean> isFavorite;

    private RecipeRepository() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
    }

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    public RecipeListLiveData getRecipes() {
        return recipes;
    }

    public RecipeLiveData getRecipe() {
        return recipe;
    }

    public void init() {
        recipes = new RecipeListLiveData(dbRef.child("users").child(FirebaseAuth.getInstance().getUid()).child("recipes"));
    }

    public void init2(String recipeId) {
        recipe = new RecipeLiveData(dbRef, recipeId);
    }

    public void uploadRecipeImage(Bitmap bitmap, String path) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + path + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> {

        });
    }

    public String addRecipe(Recipe recipe) {
        DatabaseReference reference = dbRef.child("recipes").push();
        String recipeUid = reference.getKey();
        reference.setValue(recipe);
        dbRef.child("users").child(FirebaseAuth.getInstance().getUid()).child("recipes").child(recipeUid).setValue(recipe);
        return recipeUid;
    }

    public void editRecipe(Recipe recipe) {
        String recipeId = getRecipe().getValue().getId();
        dbRef.child("users").child(FirebaseAuth.getInstance().getUid()).child("recipes").child(recipeId).updateChildren(recipe.asMap());
        dbRef.child("recipes").child(recipeId).updateChildren(recipe.asMap());
    }

    public void removeRecipe() {
        String recipeId = getRecipe().getValue().getId();
        dbRef.child("users").child(FirebaseAuth.getInstance().getUid()).child("recipes").child(recipeId).removeValue();
        dbRef.child("recipes").child(recipeId).removeValue();
    }

    public void addFavorite() {
        String recipeId = getRecipe().getValue().getId();
        dbRef.child("favorites").child(FirebaseAuth.getInstance().getUid()).child(recipeId).setValue(true);
    }

    public void removeFavorite() {
        String recipeId = getRecipe().getValue().getId();
        dbRef.child("favorites").child(FirebaseAuth.getInstance().getUid()).child(recipeId).removeValue();
    }
}
