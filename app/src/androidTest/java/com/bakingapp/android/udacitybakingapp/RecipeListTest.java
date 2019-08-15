package com.bakingapp.android.udacitybakingapp;

import com.bakingapp.android.udacitybakingapp.ui.RecipeListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListTest {

    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<RecipeListActivity> activityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    @Before
    public void registerIdlingResource() {
        idlingResource = activityTestRule.getActivity().getmIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void testDataOfRecyclerView(){
        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }


}
