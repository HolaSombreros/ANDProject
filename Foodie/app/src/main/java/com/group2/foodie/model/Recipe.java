package com.group2.foodie.model;

import java.util.List;

public class Recipe {
    private String id;
    private String name;
    private int imageId;
    private List<Ingredient> ingredients;
    private String instructions;
    private boolean isPublic;
    private String category;
    private String publisherId;
    private String publisherUsername;

    public Recipe() {

    }

    public Recipe(String name, int imageId, List<Ingredient> ingredients, String instructions, boolean isPublic, String category, String publisherId, String publisherUsername) {
        this.name = name;
        this.imageId = imageId;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.isPublic = isPublic;
        this.category = category;
        this.publisherId = publisherId;
        this.publisherUsername = publisherUsername;
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
}
