package com.bakingapp.android.udacitybakingapp.ui;

import android.os.Bundle;

import com.bakingapp.android.udacitybakingapp.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//This will only be shown on mobile devices - Video Screen
public class RecipeStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_step);
    }
}
