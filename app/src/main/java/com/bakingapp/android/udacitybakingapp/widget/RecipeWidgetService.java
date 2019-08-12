package com.bakingapp.android.udacitybakingapp.widget;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.repository.RecipeRepository;
import com.bakingapp.android.udacitybakingapp.utils.RecipePreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class RecipeWidgetService extends IntentService {

    public static final String ACTION_OPEN_RECIPE = "ACTION_OPEN_RECIPE";
    public static final String ACTION_REMOVE_RECIPE = "ACTION_REMOVE_RECIPE";

    public static final int FOREGROUND_ID = 1231;

    public RecipeWidgetService(String name) {
        super(name);
    }

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

            if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_OPEN_RECIPE.equals(action)) {
                handleActionOpenRecipe();
            } else if (ACTION_REMOVE_RECIPE.equals(action)) {
                handleActionRemoveRecipe();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.channel_id);
            String channelName = getString(R.string.channel_name);

            NotificationChannel channel = new NotificationChannel(channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle(getString(R.string.notification_service_title))
                    .setContentText(getString(R.string.notification_service_body))
                    .build();

            startForeground(FOREGROUND_ID, notification);

        }

    }

    private void handleActionOpenRecipe() {
        //Get the saved id from SharedPreferences
        int savedId = RecipePreferences.getCurrentDisplayRecipe(getApplicationContext());

        Recipe recipe = RecipeRepository.getInstance().queryAllByRecipeId(savedId);

        //Create AppWidgetManager to pass the retrieved Recipe from the repository
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        RecipeWidgetProvider.updateWidgetRecipe(this, recipe, appWidgetManager, appWidgetIds);

        stopSelf();
    }

    private void handleActionRemoveRecipe() {
        //Create AppWidgetManager to pass the retrieved Recipe from the repository
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        //Passing null to recipe
        RecipeWidgetProvider.updateWidgetRecipe(this, null, appWidgetManager, appWidgetIds);

        stopSelf();
    }

    // Trigger the service to perform the action
    public static void startActionOpenRecipe(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_OPEN_RECIPE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, intent);
        } else {
            context.startService(intent);
        }
    }

    public static void startActionRemoveRecipe(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_REMOVE_RECIPE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, intent);
        } else {
            context.startService(intent);
        }
    }


}