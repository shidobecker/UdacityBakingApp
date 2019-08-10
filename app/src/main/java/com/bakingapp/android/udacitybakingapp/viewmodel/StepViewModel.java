package com.bakingapp.android.udacitybakingapp.viewmodel;

import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.model.Step;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StepViewModel extends ViewModel {

    private MutableLiveData<Recipe> observableRecipe = new MutableLiveData<>();

    private MutableLiveData<Step> observableCurrentStep = new MutableLiveData<>();

    private MutableLiveData<Boolean> showPreviousStep = new MutableLiveData<>();

    private MutableLiveData<Boolean> showNextStep = new MutableLiveData<>();

    //Aux variable since recipe won't change in this screen
    private Recipe recipe;

    StepViewModel(Recipe recipe, Step currentStep) {
        this.recipe = recipe;
        observableRecipe.setValue(recipe);
        observableCurrentStep.setValue(currentStep);
        checkSteps();
    }


    public MutableLiveData<Recipe> getObservableRecipe() {
        return observableRecipe;
    }

    public MutableLiveData<Step> getObservableCurrentStep() {
        return observableCurrentStep;
    }


    public MutableLiveData<Boolean> getShowPreviousStep() {
        return showPreviousStep;
    }

    public MutableLiveData<Boolean> getShowNextStep() {
        return showNextStep;
    }

    public void goToNextStep() {
        Step step = observableCurrentStep.getValue();

        int indexStep = recipe.getSteps().indexOf(step);

        observableCurrentStep.setValue(recipe.getSteps().get(indexStep + 1));

        checkSteps();
    }


    public void goToPreviousStep() {
        Step step = observableCurrentStep.getValue();

        int indexStep = recipe.getSteps().indexOf(step);

        observableCurrentStep.setValue(recipe.getSteps().get(indexStep - 1));

        checkSteps();
    }

    boolean checkSteps() {
        Step step = observableCurrentStep.getValue();

        int indexStep = recipe.getSteps().indexOf(step);

        boolean hasNext = indexStep >= 0 && indexStep + 1 != recipe.getSteps().size();
        boolean hasPrevious = indexStep > 0;

        showNextStep.setValue(hasNext);
        showPreviousStep.setValue(hasPrevious);

        return hasNext;
    }


}
