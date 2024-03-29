package com.bakingapp.android.udacitybakingapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.model.Step;
import com.bakingapp.android.udacitybakingapp.testutils.RecipeIdlingResource;
import com.bakingapp.android.udacitybakingapp.viewmodel.StepViewModel;
import com.bakingapp.android.udacitybakingapp.viewmodel.StepViewModelFactory;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.test.espresso.IdlingResource;
import butterknife.BindView;
import butterknife.ButterKnife;

//This will only be shown on mobile devices - Video Screen with instructions
public class StepActivity extends AppCompatActivity {

    @BindView(R.id.step_next)
    TextView stepNext;

    @BindView(R.id.step_previous)
    TextView stepPrevious;

    private StepViewModel viewModel;

    @Nullable
    private RecipeIdlingResource mIdlingResource;

    @Nullable
    public RecipeIdlingResource getmIdlingResource() {
        return mIdlingResource;
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new RecipeIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step);

        getIdlingResource();

        ButterKnife.bind(this);

        if (getIntent().hasExtra(StepListActivity.RECIPE_EXTRA)) {
            //Recipe returned from JSON will be passed alongside with Step to ViewModel
            setupViewModel();
            setupView();
        } else {
            finish();
        }
    }

    private void setupViewModel() {
        //Initializing ViewModel with recipe and step.
        String recipeString = getIntent().getStringExtra(StepListActivity.RECIPE_EXTRA);
        String stepString = getIntent().getStringExtra(StepListActivity.STEP_EXTRA);

        Recipe recipe = new Gson().fromJson(recipeString, Recipe.class);
        Step step = new Gson().fromJson(stepString, Step.class);

        StepViewModelFactory factory = new StepViewModelFactory(recipe, step);
        viewModel = ViewModelProviders.of(this, factory).get(StepViewModel.class);

    }

    private void setupView() {
        viewModel.getObservableRecipe().observe(this, recipe -> {
            if (!getResources().getBoolean(R.bool.isLandScape)) {
                getSupportActionBar().setTitle(recipe.getName());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }else{
                getSupportActionBar().hide();
            }
        });

        viewModel.getObservableCurrentStep().observe(this, step -> {
            InstructionsFragment instructionsFragment = (InstructionsFragment)
                    getSupportFragmentManager().findFragmentByTag(InstructionsFragment.TAG);

            if (instructionsFragment == null) {
                instructionsFragment = InstructionsFragment
                        .newInstance(step.getShortDescription(),
                                step.getDescription(),
                                step.getVideoURL(),
                                step.getThumbnailURL());

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_player_container, instructionsFragment, InstructionsFragment.TAG)
                        .commit();
            }
        });

        viewModel.getShowNextStep().observe(this, show -> {
            if (show)
                stepNext.setVisibility(View.VISIBLE);
            else
                stepNext.setVisibility(View.GONE);
        });

        viewModel.getShowPreviousStep().observe(this, show -> {
            if (show)
                stepPrevious.setVisibility(View.VISIBLE);
            else
                stepPrevious.setVisibility(View.GONE);
        });

        stepNext.setOnClickListener(view -> viewModel.goToNextStep());

        stepPrevious.setOnClickListener(view -> viewModel.goToPreviousStep());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
