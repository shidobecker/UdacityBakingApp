package com.bakingapp.android.udacitybakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.ui.StepListActivity;
import com.google.gson.Gson;

public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String WIDGET_RECIPE_EXTRA = "WIDGET_RECIPE_EXTRA";

    public static void updateAppWidget(Context context, Recipe recipe,
                                       AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients);

        Intent intent = new Intent(context, StepListActivity.class);

        String recipeString = new Gson().toJson(recipe);

        intent.putExtra(WIDGET_RECIPE_EXTRA, recipeString);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        if (recipe != null) {
            //Filling the Recipe name RemoteView
            views.setTextViewText(R.id.widget_recipe_name, recipe.getName());

            //This starts the RemoteViewsFactory to fill our ingredient list
            Intent remoteViewsIntent = new Intent(context, RecipeRemoteViewsService.class);
            views.setRemoteAdapter(R.id.widgetListView, remoteViewsIntent);

            views.setViewVisibility(R.id.widget_ingredients_container, View.VISIBLE);
            views.setOnClickPendingIntent(R.id.widget_recipe_name, pendingIntent);
        } else {
            views.setTextViewText(R.id.widget_recipe_name, context.getString(R.string.no_recipe_widget));
            views.setViewVisibility(R.id.widget_ingredients_container, View.GONE);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetListView);


    }

    //Update All widgets passing that recipe
    public static void updateWidgetRecipe(Context context, Recipe recipe,
                                          AppWidgetManager appWidgetManager,
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
