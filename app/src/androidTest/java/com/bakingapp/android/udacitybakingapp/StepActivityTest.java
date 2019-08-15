package com.bakingapp.android.udacitybakingapp;

import android.content.Context;
import android.content.Intent;

import com.bakingapp.android.udacitybakingapp.model.Ingredient;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.model.Step;
import com.bakingapp.android.udacitybakingapp.ui.StepActivity;
import com.bakingapp.android.udacitybakingapp.ui.StepListActivity;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.rule.ActivityTestRule;
import io.realm.RealmList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4.class)
public class StepActivityTest {

    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<StepActivity> mActivityRule = new ActivityTestRule<StepActivity>(
            StepActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = ApplicationProvider.getApplicationContext();
            //Mocking the intent extra values necessary to open this activity
            Intent result = new Intent(targetContext, StepActivity.class);
            result.putExtra(StepListActivity.RECIPE_EXTRA, new Gson().toJson(mockRecipe()));
            result.putExtra(StepListActivity.STEP_EXTRA, new Gson().toJson(mockStep()));
            return result;
        }
    };

    private Recipe mockRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Recipe");
        recipe.setId(0);
        recipe.setServings(2);

        Step step = new Step("Short Description",
                "Description",
                "Video URl", "");

        Ingredient ingredient = new Ingredient(0, 1, "G", "Name");

        recipe.setSteps(new RealmList<>(step));
        recipe.setIngredients(new RealmList<>(ingredient));

        return recipe;
    }

    private Step mockStep() {
        return new Step("Short Description",
                "Description",
                "Video URl", "");
    }


    @Before
    public void before() {
        idlingResource = mActivityRule.getActivity().getmIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void checkForDisplayedViews() {
        onView(withId(R.id.step_next)).check(matches(isDisplayed()));
        onView(withId(R.id.step_player_container)).check(matches(isDisplayed()));
    }


    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }


}
