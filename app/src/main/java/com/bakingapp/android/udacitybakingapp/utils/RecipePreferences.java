package com.bakingapp.android.udacitybakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class RecipePreferences {

    private static final String PREFS_KEY = "BAKING_APP_PREFS";

    private static final String RECIPE_ID = "RECIPE_ID";

    private static SharedPreferences sharedPrefs;

    private static SharedPreferences getPrefs(Context context) {

        if (sharedPrefs == null) {
            sharedPrefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        }
        return sharedPrefs;
    }

    public static void setCurrentDisplayRecipe(Context context, int id) {
        getPrefs(context).edit().putInt(RECIPE_ID, id).apply();
    }

    public static int getCurrentDisplayRecipe(Context context) {
        return getPrefs(context).getInt(RECIPE_ID, -1);
    }

}
