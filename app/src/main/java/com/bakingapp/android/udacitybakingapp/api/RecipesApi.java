package com.bakingapp.android.udacitybakingapp.api;

import com.bakingapp.android.udacitybakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesApi {

    String RECIPES_BASE_PATH = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    @GET("baking.json")
    Call<List<Recipe>> getAllRecipes();



}
