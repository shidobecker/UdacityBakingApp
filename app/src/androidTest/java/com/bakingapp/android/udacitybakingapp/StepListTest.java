package com.bakingapp.android.udacitybakingapp;

import android.content.Context;
import android.content.Intent;

import com.bakingapp.android.udacitybakingapp.model.Ingredient;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.model.Step;
import com.bakingapp.android.udacitybakingapp.ui.RecipeListActivity;
import com.bakingapp.android.udacitybakingapp.ui.StepListActivity;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import io.realm.RealmList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class StepListTest {

    private boolean isTablet;


  @Rule
    public ActivityTestRule<StepListActivity> stepListActivityActivityTestRule =
            new ActivityTestRule<StepListActivity>(StepListActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = ApplicationProvider.getApplicationContext();
                    //Mocking the intent extra values necessary to open this activity
                    Recipe recipe = mockRecipe();

                    Intent result = new Intent(targetContext, StepListActivity.class);
                    result.putExtra(RecipeListActivity.RECIPE_EXTRA, new Gson().toJson(recipe));
                    return result;
                }
            };


  private Recipe mockRecipe(){
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

    @Before
    public void getScreenSize(){
        Context targetContext = ApplicationProvider.getApplicationContext();
        targetContext.getResources().getBoolean(R.bool.isTablet);
        isTablet = targetContext.getResources().getBoolean(R.bool.isTablet);

    }

    @Test
    public void checkForDisplayedViewsOnTableAndPhone(){

        if (isTablet) {
            onView(withId(R.id.step_recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

            onView(withId(R.id.step_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.step_list_divider)).check(matches(isDisplayed()));

        } else {
            onView(withId(R.id.step_recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

            onView(withId(R.id.step_player_container)).check(matches(isDisplayed()));

        }
    }




}
