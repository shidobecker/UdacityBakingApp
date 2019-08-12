package com.bakingapp.android.udacitybakingapp;

import android.app.Application;

import io.realm.Realm;

public class RecipeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
    }
}
