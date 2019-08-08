package com.bakingapp.android.udacitybakingapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.model.Step;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListActivity extends AppCompatActivity implements StepListFragment.OnStepClickListener{

    private boolean isTabletSize;

    private static final String TAG = StepListActivity.class.getSimpleName();

    @BindView(R.id.step_list_fragment_container)
    FrameLayout stepListContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

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

        StepListFragment fragment = StepListFragment.newInstance(recipe);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_list_fragment_container, fragment)
                .commit();

        isTabletSize = getResources().getBoolean(R.bool.isTablet);

        if(isTabletSize){
            populateFragments();
        }

    }

    private void populateFragments(){

    }

    @Override
    public void onStepSelected(Step step) {
        //TODO: START ACTIVITY AND CHECK FOR FRAGMENT);

        if(isTabletSize){
            handleStepChangeForTablet();
        }else{
            handleStepChangeForMobile();
        }
    }

    private void handleStepChangeForMobile() {
        Log.e(TAG,"Handle For mobile");
    }

    private void handleStepChangeForTablet() {
        Log.e(TAG,"Handle For tablet");

    }
}
