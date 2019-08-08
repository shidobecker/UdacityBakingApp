package com.bakingapp.android.udacitybakingapp.ui;

import android.os.Bundle;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Step;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

//This will only be shown on mobile devices - Video Screen with instructions
public class StepActivity extends AppCompatActivity {

    private String recipeName;

    private Step step;

    //TODO: Current Step to NAVIGATE
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step);

        ButterKnife.bind(this);

        if (getIntent().hasExtra(StepListActivity.RECIPE_NAME_EXTRA)) {
            //Recipe returned from JSON
            recipeName = getIntent().getStringExtra(StepListActivity.RECIPE_NAME_EXTRA);
            String stepString = getIntent().getStringExtra(StepListActivity.STEP_EXTRA);
            step = new Gson().fromJson(stepString, Step.class);
            setupView();
        } else {
            finish();
        }
    }

    private void setupView() {
        getSupportActionBar().setTitle(recipeName);

        InstructionsFragment instructionsFragment = InstructionsFragment
                .newInstance(step.getShortDescription(),
                        step.getDescription(),
                        step.getVideoURL(),
                        step.getThumbnailURL());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_player_container, instructionsFragment)
                .commit();

    }


}
