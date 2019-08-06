package com.bakingapp.android.udacitybakingapp.ui;

import android.os.Bundle;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.testutils.RecipeIdlingResource;
import com.bakingapp.android.udacitybakingapp.viewmodel.RecipeListViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


public class RecipeListActivity extends AppCompatActivity {

    private String TAG = RecipeListActivity.class.getSimpleName();

    @Nullable
    private RecipeIdlingResource mIdlingResource;

    private RecipeListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        viewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        viewModel.getAllRecipes();
    }
}
