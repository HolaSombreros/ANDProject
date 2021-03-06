package com.group2.foodie.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Recipe {
    private String id;
    private String name;
    private int imageId;
    private List<Ingredient> ingredients;
    private String instructions;
    @Exclude private boolean isFavorite;
    private boolean isPublic;
    private String category;
    private String publisherId;
    @Exclude private String publisherUsername;

    public Recipe() {

    }

    public Recipe(String name, int imageId, List<Ingredient> ingredients, String instructions, boolean isPublic, String category, String publisherId) {
        this.name = name;
        this.imageId = imageId;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.isPublic = isPublic;
        this.category = category;
        this.publisherId = publisherId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        String output = String.format("%s (%s)", name, category);
        for (Ingredient i : ingredients) {
            output += "\n" + i.toString();
        }

        output += "\nInstructions:\n" + instructions;

        return output;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherUsername() {
        return publisherUsername;
    }

    public void setPublisherUsername(String publisherUsername) {
        this.publisherUsername = publisherUsername;
    }

    @Exclude
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put("name", name);
        output.put("imageId", imageId);
        output.put("ingredients", ingredients);
        output.put("instructions", instructions);
        output.put("public", isPublic);
        output.put("category", category);
        output.put("publisherId", publisherId);
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return recipe.id.equals(this.id);
    }

}
