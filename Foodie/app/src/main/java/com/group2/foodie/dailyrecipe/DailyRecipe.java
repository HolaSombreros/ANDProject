package com.group2.foodie.dailyrecipe;
import java.util.List;

public class DailyRecipe {
    private String title;
    private List<ExtendedIngredient> extendedIngredients;
    private int readyInMinutes;
    private String instructions;
    private String image;
    private int servings;
    private String date;

    public DailyRecipe() {}

    public DailyRecipe(String title, List<ExtendedIngredient> extendedIngredients, int readyInMinutes, String instructions, String image, int servings, String date) {
        this.title = title;
        this.extendedIngredients = extendedIngredients;
        this.readyInMinutes = readyInMinutes;
        this.instructions = instructions;
        this.image = image;
        this.servings = servings;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(List<ExtendedIngredient> ingredients) {
        this.extendedIngredients = ingredients;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    @Override
    public String toString() {
        return "DailyRecipe{" +
                "title='" + title + '\'' +
                ", ingredients=" + extendedIngredients +
                ", readyInMinutes=" + readyInMinutes +
                ", instructions=" + instructions +
                ", imageUrl='" + image + '\'' +
                ", servings=" + servings +
                ", date=" + date +
                '}';
    }
}
