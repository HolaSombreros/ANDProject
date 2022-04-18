package com.group2.foodie.repository;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.livedata.ShoppingListLiveData;
import com.group2.foodie.model.Ingredient;

public class ShoppingListRepository {
    private static ShoppingListRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ShoppingListLiveData shoppingListLiveData;

    public ShoppingListRepository(){
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    public static ShoppingListRepository getInstance() {
        if (instance == null) {
            instance = new ShoppingListRepository();
        }
        return instance;
    }

    public void init() {
        shoppingListLiveData = new ShoppingListLiveData(databaseReference.child("shoppinglist").child(FirebaseAuth.getInstance().getUid()));
    }

    public ShoppingListLiveData getShoppingListLiveData() {
        return shoppingListLiveData;
    }

    public void addShoppingListIngredient(Ingredient ingredient) {
        String userId = FirebaseAuth.getInstance().getUid();
        databaseReference.child("shoppinglist").child(userId).push().setValue(ingredient);
    }
    public void removeShoppingListIngredient(Ingredient ingredient) {
        String userId = FirebaseAuth.getInstance().getUid();
        databaseReference.child("shoppinglist").child(userId).child(ingredient.getId()).removeValue();
    }
}
