package com.bakingapp.android.udacitybakingapp.viewmodel;

import android.util.Log;

import com.bakingapp.android.udacitybakingapp.api.RecipesApi;
import com.bakingapp.android.udacitybakingapp.api.RetrofitClient;
import com.bakingapp.android.udacitybakingapp.model.Recipe;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListViewModel extends ViewModel {

    private static String TAG = RecipeListViewModel.class.getSimpleName();

    private MutableLiveData<List<Recipe>> observableRecipes = new MutableLiveData<>();

    public MutableLiveData<List<Recipe>> getObservableRecipes() {
        return observableRecipes;
    }

    private void fetchRecipesFromApi() {
        RecipesApi api = RetrofitClient.getInstance().getRetrofit().create(RecipesApi.class);
        Call<List<Recipe>> call = api.getAllRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {

                    observableRecipes.postValue(response.body());

                } else {
                    observableRecipes.postValue(null);
                    Log.e(TAG, "Error on response" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                observableRecipes.postValue(null);
                Log.e(TAG, "Failure");

            }
        });
    }

    public void getAllRecipes() {

        fetchRecipesFromApi();
    }

    private boolean hasAnyRecipeSaved(){
        return true;
    }


    private void saveAllPlants(){

      /*  //Saves all recipes on ContentProvider if there's any saved
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.RecipeIngredientEntry.COLUMN_RECIPE_ID, plantType);
        contentValues.put(RecipeContract.RecipeIngredientEntry.COLUMN_RECIPE_ID, timeNow);
        contentValues.put(RecipeContract.RecipeIngredientEntry.COLUMN_RECIPE_ID, timeNow);
        contentValues.put(RecipeContract.RecipeIngredientEntry.COLUMN_RECIPE_ID, timeNow);

        getContentResolver().insert(PlantContract.PlantEntry.CONTENT_URI, contentValues);*/
    }


}
