package com.bakingapp.android.udacitybakingapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class RecipeContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.bakingapp.android.udacitybakingapp";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "plants" directory
    public static final String PATH_RECIPE_INGREDIENT = "recipe_ingredient";

    public static final long INVALID_RECIPE_ID = -1;

    public static final class RecipeIngredientEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE_INGREDIENT).build();

        public static final String TABLE_NAME = "recipeStep";
        public static final String COLUMN_RECIPE_ID = "recipeId";
        public static final String COLUMN_INGREDIENT_QUANTITY = "ingredientQuantity";
        public static final String COLUMN_INGREDIENT_MEASURE = "ingredientMeasure";
        public static final String COLUMN_INGREDIENT_NAME = "ingredientName";
    }


}
