package com.group2.foodie.repository;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.group2.foodie.livedata.CategoriesLiveData;
import com.group2.foodie.livedata.FavoriteLiveData;
import com.group2.foodie.livedata.PersonalRecipesLiveData;
import com.group2.foodie.livedata.PublicPersonalRecipesLiveData;
import com.group2.foodie.livedata.PublicRecipesLiveData;
import com.group2.foodie.livedata.SpecificRecipeLiveData;
import com.group2.foodie.model.Recipe;

import java.io.ByteArrayOutputStream;

public class RecipeRepository {
    private static RecipeRepository instance;
    private DatabaseReference dbRef;
    private CategoriesLiveData categories;
    private PersonalRecipesLiveData personalRecipes;
    private PublicRecipesLiveData publicRecipes;
    private SpecificRecipeLiveData specificRecipe;
    private FavoriteLiveData favorite;
    private PublicPersonalRecipesLiveData publicPersonalRecipes;

    private RecipeRepository() {
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }

        return instance;
    }

    public PersonalRecipesLiveData getPersonalRecipes() {
        return personalRecipes;
    }

    public PublicRecipesLiveData getPublicRecipes() {
        return publicRecipes;
    }

    public FavoriteLiveData getFavorite() {
        return favorite;
    }

    public SpecificRecipeLiveData getSpecificRecipe() {
        return specificRecipe;
    }

    public CategoriesLiveData getRecipeCategories() {
        return categories;
    }

    public PublicPersonalRecipesLiveData getPublicPersonalRecipes() {
        return publicPersonalRecipes;
    }

    public void init() {
        personalRecipes = new PersonalRecipesLiveData(dbRef, FirebaseAuth.getInstance().getUid());
        publicRecipes = new PublicRecipesLiveData(dbRef);
        categories = new CategoriesLiveData(dbRef);
    }

    public void init(String publisherId, String recipeId) {
        specificRecipe = new SpecificRecipeLiveData(dbRef, publisherId, recipeId);
        categories = new CategoriesLiveData(dbRef);
        favorite = new FavoriteLiveData(dbRef, recipeId);
    }

    public void init(String uid){
        publicPersonalRecipes = new PublicPersonalRecipesLiveData(dbRef, uid);
    }

    public UploadTask uploadRecipeImage(Bitmap bitmap, String path) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/recipes/" + path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return storageRef.putBytes(data);
    }

    public String addRecipe(Recipe recipe) {
        DatabaseReference reference = dbRef.child("personalrecipes").child(recipe.getPublisherId()).push();
        String recipeId = reference.getKey();
        reference.setValue(recipe);

        if (recipe.isPublic()) {
            dbRef.child("publicrecipes").child(recipeId).setValue(recipe);
        }

        return recipeId;
    }

    public void editRecipe(Recipe recipe) {
        String recipeId = getSpecificRecipe().getValue().getId();
        dbRef.child("personalrecipes").child(recipe.getPublisherId()).child(recipeId).updateChildren(recipe.asMap());

        if (recipe.isPublic()) {
            dbRef.child("publicrecipes").child(recipeId).updateChildren(recipe.asMap());
        } else {
            dbRef.child("publicrecipes").child(recipeId).get().addOnCompleteListener(task -> {
                if (task.getResult().exists()) {
                    dbRef.child("publicrecipes").child(recipeId).removeValue();
                }
            });
        }
    }

    public void removeRecipe() {
        Recipe recipe = getSpecificRecipe().getValue();
        dbRef.child("personalrecipes").child(recipe.getPublisherId()).child(recipe.getId()).removeValue();
        dbRef.child("publicrecipes").child(recipe.getId()).removeValue();
    }

    public void changeFavorite() {
        String recipeId = getSpecificRecipe().getValue().getId();
        if (favorite.getValue()) {
            dbRef.child("favorites").child(FirebaseAuth.getInstance().getUid()).child(recipeId).removeValue();
        } else {
            dbRef.child("favorites").child(FirebaseAuth.getInstance().getUid()).child(recipeId).setValue(true);
        }
    }

}
