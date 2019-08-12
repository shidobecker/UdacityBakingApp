package com.bakingapp.android.udacitybakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Ingredient;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.ui.StepListActivity;
import com.google.gson.Gson;

public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String WIDGET_RECIPE_EXTRA = "WIDGET_RECIPE_EXTRA";


    public static void updateAppWidget(Context context, Recipe recipe,
                                       AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients);

        Intent intent = new Intent(context, StepListActivity.class);
        intent.putExtra(WIDGET_RECIPE_EXTRA, new Gson().toJson(recipe));

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        if (recipe != null) {
            views.setTextViewText(R.id.widget_recipe_name, recipe.getName());

            //List of ingredients
            for (Ingredient ingredient : recipe.getIngredients()) {
                RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                        R.layout.widget_ingredient_list_item);

                String quantityText = String.valueOf(ingredient.getQuantity());
                String measureText = ingredient.getMeasure();
                String nameText = ingredient.getName();
                StringBuilder ingredientText = new StringBuilder()
                        .append("(")
                        .append(quantityText)
                        .append(" ")
                        .append(measureText)
                        .append(") ")
                        .append(nameText);

                ingredientView.setTextViewText(R.id.widget_ingredient_name, ingredientText);

                views.addView(R.id.widget_ingredients_container, ingredientView);
            }

            views.setOnClickPendingIntent(R.id.widget_ingredients_container, pendingIntent);
        } else {
            views.setTextViewText(R.id.widget_recipe_name, context.getString(R.string.no_recipe_widget));
            views.removeAllViews(R.id.widget_ingredients_container);

        }


        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    public static void updateWidgetRecipe(Context context, Recipe recipe, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, recipe, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeWidgetService.startActionOpenRecipe(context);
    }
}
