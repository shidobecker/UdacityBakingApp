package com.bakingapp.android.udacitybakingapp.viewmodel;

import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.model.Step;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StepViewModelFactory   extends ViewModelProvider.NewInstanceFactory {

    private Recipe recipe;

    private Step currentStep;


    public StepViewModelFactory(Recipe recipe, Step currentStep) {
        this.recipe = recipe;
        this.currentStep = currentStep;

    }


    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new StepViewModel(recipe, currentStep);
    }

}
