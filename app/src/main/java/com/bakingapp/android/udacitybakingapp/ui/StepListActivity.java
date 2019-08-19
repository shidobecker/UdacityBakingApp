package com.bakingapp.android.udacitybakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.model.Step;
import com.bakingapp.android.udacitybakingapp.repository.RecipeRepository;
import com.bakingapp.android.udacitybakingapp.utils.RecipePreferences;
import com.bakingapp.android.udacitybakingapp.widget.RecipeWidgetProvider;
import com.bakingapp.android.udacitybakingapp.widget.RecipeWidgetService;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListActivity extends AppCompatActivity implements StepListFragment.OnStepClickListener {

    private boolean isTabletSize;

    private static final String TAG = StepListActivity.class.getSimpleName();

    @BindView(R.id.step_list_fragment_container)
    FrameLayout stepListContainer;

    public static final String RECIPE_EXTRA = "RECIPE_EXTRA";

    public static final String STEP_EXTRA = "STEP_EXTRA";

    Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        ButterKnife.bind(this);

        if (getIntent().hasExtra(RecipeListActivity.RECIPE_EXTRA)) {
            //Recipe returned from JSON from previous activity
            String recipeString = getIntent().getStringExtra(RecipeListActivity.RECIPE_EXTRA);
            recipe = new Gson().fromJson(recipeString, Recipe.class);
            setupView();
        } else if (getIntent().hasExtra(RecipeWidgetProvider.WIDGET_RECIPE_EXTRA)) {
            //Recipe returned from JSON that came from widget
            String recipeString = getIntent().getStringExtra(RecipeWidgetProvider.WIDGET_RECIPE_EXTRA);
            recipe = new Gson().fromJson(recipeString, Recipe.class);
            setupView();

        } else {
            finish();
        }
    }

    private void setupView() {
        getSupportActionBar().setTitle(recipe.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StepListFragment fragment = (StepListFragment) getSupportFragmentManager()
                .findFragmentByTag(StepListFragment.TAG);

        if (fragment == null) {
            fragment = StepListFragment.newInstance(recipe);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_list_fragment_container, fragment, StepListFragment.TAG)
                    .commit();
        }

        isTabletSize = getResources().getBoolean(R.bool.isTablet);

        if (isTabletSize) {
            populateFragments();
        }

    }

    private void populateFragments() {
        InstructionsFragment instructionsFragment = (InstructionsFragment)
                getSupportFragmentManager().findFragmentByTag(InstructionsFragment.TAG);

        if (instructionsFragment == null) {
            Step firstStep = recipe.getSteps().get(0);
            instructionsFragment = InstructionsFragment
                    .newInstance(firstStep.getShortDescription(),
                            firstStep.getDescription(),
                            firstStep.getVideoURL(),
                            firstStep.getThumbnailURL());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_player_container, instructionsFragment, InstructionsFragment.TAG)
                    .commit();

        }

    }

    @Override
    public void onStepSelected(Step step) {
        if (isTabletSize) {
            handleStepChangeForTablet(step);
        } else {
            handleStepChangeForMobile(step);
        }
    }

    private void handleStepChangeForMobile(Step step) {
        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra(RECIPE_EXTRA, new Gson().toJson(recipe));
        intent.putExtra(STEP_EXTRA, new Gson().toJson(step));

        startActivity(intent);
    }

    private void handleStepChangeForTablet(Step step) {
        InstructionsFragment instructionsFragment = InstructionsFragment
                .newInstance(step.getShortDescription(),
                        step.getDescription(),
                        step.getVideoURL(),
                        step.getThumbnailURL());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_player_container, instructionsFragment)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.step_list_menu, menu);

        if (RecipePreferences.getCurrentDisplayRecipe(this) == recipe.getId()) {
            menu.getItem(0).setIcon(R.drawable.ic_bookmark_black);
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_bookmark) {
            //Saving that Id on SharedPreferences
            if (RecipePreferences.getCurrentDisplayRecipe(this) != recipe.getId()) {
                RecipePreferences.setCurrentDisplayRecipe(this, recipe.getId());
                item.setIcon(R.drawable.ic_bookmark_black);
                saveRecipe();
            } else {
                RecipePreferences.setCurrentDisplayRecipe(this, -1);
                item.setIcon(R.drawable.ic_bookmark_white);
                removeRecipe();
            }
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void saveRecipe() {
        RecipeRepository.getInstance().saveRecipe(recipe);
        RecipeWidgetService.startActionOpenRecipe(this);
    }

    private void removeRecipe() {
        RecipeRepository.getInstance().removeRecipe();
        RecipeWidgetService.startActionRemoveRecipe(this);
    }


}
