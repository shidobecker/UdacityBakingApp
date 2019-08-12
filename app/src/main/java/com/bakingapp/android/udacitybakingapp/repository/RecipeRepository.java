package com.bakingapp.android.udacitybakingapp.repository;

import com.bakingapp.android.udacitybakingapp.model.RecipeIngredients;

import java.util.List;

import io.realm.Realm;

/**
 * Singleton responsible for all access into local database
 */
public class RecipeRepository {

    private static RecipeRepository instance = null;

    private RecipeRepository() {
    }


    public void saveRecipes(List<RecipeIngredients> recipeList) {
        removeRecipe();

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(realm1 -> realm1.copyToRealm(recipeList));

    }

    public List<RecipeIngredients> queryAllByRecipeId(int recipeId) {
        Realm realm = Realm.getDefaultInstance();

        return realm.copyFromRealm(realm.where((RecipeIngredients.class))
                .equalTo("recipeId", recipeId).findAll());

    }

    public void removeRecipe() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.delete(RecipeIngredients.class));
    }


    public static synchronized RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

}
