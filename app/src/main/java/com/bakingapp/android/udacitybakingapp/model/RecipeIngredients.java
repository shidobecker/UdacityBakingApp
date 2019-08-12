package com.bakingapp.android.udacitybakingapp.model;

import io.realm.RealmObject;

//This Realm model will be used to show Ingredient list for a specific Recipe in offline mode,
// instead of using the same objects from response
public class RecipeIngredients extends RealmObject {

    private int recipeId;

    private String recipeName;

    private String ingredient;

    private float quantity;

    private String measure;

    public RecipeIngredients() {
    }

    public RecipeIngredients(int recipeId, String recipeName, String ingredient, float quantity, String measure) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.measure = measure;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
