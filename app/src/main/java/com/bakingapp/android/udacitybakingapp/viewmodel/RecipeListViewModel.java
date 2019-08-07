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
        //Log.e(TAG, call.request().url().toString());

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {

                    observableRecipes.postValue(response.body());

                    for (Recipe r : response.body()) {
                        Log.e(TAG, r.getName());
                    }

                    //Saving all recipes to be searched locally afterwards
                /*    AppExecutors.getInstance().diskIO().execute(() -> {
                        database.getRecipeDao().deleteAllRecipes();
                        for (Recipe r : response.body()) {
                            database.getRecipeDao().saveRecipe(r);
                        }
                    });
*/

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

}
