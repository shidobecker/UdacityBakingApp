package com.bakingapp.android.udacitybakingapp.widget;

import android.app.IntentService;
import android.content.Intent;

public class RecipeWidgetService extends IntentService {

    public RecipeWidgetService(String name) {
        super(name);
    }

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
