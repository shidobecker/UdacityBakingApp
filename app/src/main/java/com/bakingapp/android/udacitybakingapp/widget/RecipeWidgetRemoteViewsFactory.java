package com.bakingapp.android.udacitybakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Ingredient;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;


public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Ingredient> ingredientList = new ArrayList<>();

    RecipeWidgetRemoteViewsFactory(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredientList = new ArrayList<>();

        Recipe recipe = RecipeRepository.getInstance().querySavedRecipe();
        if(recipe == null){
            ingredientList = new ArrayList<>();
        }else{
            ingredientList = recipe.getIngredients();
        }

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return ingredientList ==null ? 0 : ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                R.layout.widget_ingredient_list_item);

        if (ingredientList.isEmpty()) {
            ingredientView.removeAllViews(R.id.widget_ingredient_name);
        }else {
            String quantityText = String.valueOf(ingredientList.get(i).getQuantity());
            String measureText = ingredientList.get(i).getMeasure();
            String nameText = ingredientList.get(i).getName();
            StringBuilder ingredientText = new StringBuilder()
                    .append("(")
                    .append(quantityText)
                    .append(" ")
                    .append(measureText)
                    .append(") ")
                    .append(nameText);

            ingredientView.setTextViewText(R.id.widget_ingredient_name, ingredientText);
        }
        return ingredientView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
