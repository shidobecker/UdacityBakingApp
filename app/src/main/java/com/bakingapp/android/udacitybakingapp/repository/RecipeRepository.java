package com.bakingapp.android.udacitybakingapp.repository;

import com.bakingapp.android.udacitybakingapp.model.Recipe;

import io.realm.Realm;

/**
 * Singleton responsible for all access into local database
 */
public class RecipeRepository {

    private static RecipeRepository instance = null;

    private RecipeRepository() {
    }


    public void saveRecipe(Recipe recipe) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.delete(Recipe.class));

        realm.executeTransaction(realm1 -> realm1.copyToRealm(recipe));

        realm.close();

    }

    //Since is called by service, no need to use App executors
    public Recipe queryAllByRecipeId(int recipeId) {
        Realm realm = Realm.getDefaultInstance();

        Recipe recipe = realm.where((Recipe.class)).equalTo("id", recipeId).findFirst();

        if (recipe != null) {
            return realm.copyFromRealm(recipe);
        } else {
            return null;
        }

    }

    public void removeRecipe() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.delete(Recipe.class));

        realm.close();

    }


    public static synchronized RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

}
