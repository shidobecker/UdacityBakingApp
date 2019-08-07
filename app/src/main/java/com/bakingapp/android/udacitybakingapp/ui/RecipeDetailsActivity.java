package com.bakingapp.android.udacitybakingapp.ui;

import android.os.Bundle;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        if (getIntent().hasExtra(RecipeListActivity.RECIPE_EXTRA)) {
            //Recipe returned from JSON
            String recipeString = getIntent().getStringExtra(RecipeListActivity.RECIPE_EXTRA);
            Recipe recipe = new Gson().fromJson(recipeString, Recipe.class);
            setupView(recipe);
        } else {
            finish();
        }
    }

    private void setupView(Recipe recipe){
        getSupportActionBar().setTitle(recipe.getName());



    }


}
