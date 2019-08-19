package com.bakingapp.android.udacitybakingapp.repository;

import com.bakingapp.android.udacitybakingapp.model.Ingredient;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.model.Step;

import java.util.List;

import io.realm.Realm;

/**
 * Singleton responsible for all access into local database
 */
public class RecipeRepository {

    private static RecipeRepository instance = null;

    private RecipeRepository() {
    }

    public interface OnRecipeSavedSuccess {
        void onSuccess();
    }

    public void saveRecipe(Recipe recipe, OnRecipeSavedSuccess listener) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.delete(Recipe.class);
            realm1.copyToRealm(recipe);
            listener.onSuccess();
        });

        realm.close();
    }

    //Since is called by service, no need to use App executors
    //Widget can only display one Recipe at a time, so no need to find by id
    public Recipe querySavedRecipe() {
        Realm realm = Realm.getDefaultInstance();

        Recipe recipeOnRealm = realm.where((Recipe.class))
                .findFirst();

        Recipe recipe = new Recipe();
        if (recipeOnRealm != null) {
            recipe = realm.copyFromRealm(recipeOnRealm);
        }
        return recipe;
    }


    public void removeRecipe() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.delete(Recipe.class);
            realm1.delete(Step.class);
            realm1.delete(Ingredient.class);
        });

        realm.close();

    }


    public static synchronized RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

}
