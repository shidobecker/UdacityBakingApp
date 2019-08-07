package com.bakingapp.android.udacitybakingapp.repository;


import com.bakingapp.android.udacitybakingapp.model.Recipe;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe WHERE id = :id")
    LiveData<Recipe> fetchRecipeById(int id);

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> fetchAllRecipes();

    //This will be used from widget
    @Query("SELECT * FROM recipe WHERE id = :id")
    List<Recipe> fetchRecipe(int id);

    @Insert
    void saveRecipe(Recipe recipe);

    @Delete
    void deleteAllRecipes();


}
