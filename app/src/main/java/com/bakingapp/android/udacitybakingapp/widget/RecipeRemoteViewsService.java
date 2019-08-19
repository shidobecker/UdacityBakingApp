package com.bakingapp.android.udacitybakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.bakingapp.android.udacitybakingapp.model.Ingredient;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.google.gson.Gson;

import java.util.List;

public class RecipeRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewsFactory(this.getApplicationContext());
    }


}
