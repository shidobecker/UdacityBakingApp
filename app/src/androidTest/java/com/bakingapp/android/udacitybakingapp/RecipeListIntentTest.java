package com.bakingapp.android.udacitybakingapp;

import android.app.Activity;
import android.app.Instrumentation;

import com.bakingapp.android.udacitybakingapp.ui.RecipeListActivity;
import com.bakingapp.android.udacitybakingapp.ui.StepListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4.class)
public class RecipeListIntentTest {

    @Rule
    public IntentsTestRule<RecipeListActivity> mActivityRule = new IntentsTestRule<>(
            RecipeListActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void intentTest(){

        // Let the UI load completely first
    /*   try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        //Recyclerview scroll to position
        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.scrollToPosition(4));

        //Perform Recyclerview click on item at position
        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Check if intent (RecipeActivity to StepListActivity) has RECIPE_INTENT_EXTRA
        intended(hasExtraWithKey(RecipeListActivity.RECIPE_EXTRA));

        //Check if intent (RecipeDetailsActivity to CookingActivity) has RECIPE_INTENT_EXTRA
        intended(hasComponent(StepListActivity.class.getName()));
    }

}
