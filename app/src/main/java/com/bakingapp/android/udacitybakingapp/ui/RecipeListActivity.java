package com.bakingapp.android.udacitybakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.testutils.RecipeIdlingResource;
import com.bakingapp.android.udacitybakingapp.viewmodel.RecipeListViewModel;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeListActivity extends AppCompatActivity {

    private String TAG = RecipeListActivity.class.getSimpleName();

    @Nullable
    private RecipeIdlingResource mIdlingResource;

    private RecipeListViewModel viewModel;

    @BindView(R.id.recipes_recycler_view)
    RecyclerView recipesRecyclerView;

    @BindView(R.id.recipe_list_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.recipe_list_error_message)
    TextView errorMessage;

    private RecipeListAdapter adapter;

    public static final String RECIPE_EXTRA = "RECIPE_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        viewModel.getAllRecipes();

        observeRecipes();
    }


    private void observeRecipes() {
        adapter = new RecipeListAdapter(recipe -> {
            String recipeJson = new Gson().toJson(recipe);
            startDetailActivity(recipeJson);
        });

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        if (tabletSize) {
            recipesRecyclerView.setLayoutManager(new GridLayoutManager(this, calculateNoOfColumns()));
        } else {
            recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        recipesRecyclerView.setAdapter(adapter);

        viewModel.getObservableRecipes().observe(this, (recipes -> {
            if (recipes == null) {
                showErrorMessage();
            } else {
                showRecipeList(recipes);
            }
        }));


    }

    private void startDetailActivity(String recipeJson){
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipeJson);
        startActivity(intent);
    }

    private void showRecipeList(List<Recipe> recipes) {
        adapter.setRecipes(recipes);
        progressBar.setVisibility(View.GONE);
        recipesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        progressBar.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
        recipesRecyclerView.setVisibility(View.GONE);
    }

    private int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 300;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if (noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

}
