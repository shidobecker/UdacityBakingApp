package com.bakingapp.android.udacitybakingapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {

    private List<Recipe> recipes = new ArrayList<>();

    public RecipeListAdapter(RecipeClickListener listener) {
        this.listener = listener;
    }

    interface RecipeClickListener{
        void onClickRecipe(Recipe recipe);
    }

    private RecipeClickListener listener;

    void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_item_image)
        ImageView image;

        @BindView(R.id.recipe_item_name)
        TextView name;

        @BindView(R.id.recipe_item_number_of_steps)
        TextView numberOfSteps;

        @BindView(R.id.recipe_item_serving)
        TextView serving;

        @BindView(R.id.recipe_item_container)
        ConstraintLayout container;

        RecipeListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void bind(Recipe recipe) {
            name.setText(recipe.getName());
            setImage(recipe.getImage());

            String numberOfStepsText = itemView.getContext().getString(R.string.number_of_steps) + String.valueOf(recipe.getSteps().size());
            numberOfSteps.setText(numberOfStepsText);

            String servingText = itemView.getContext().getString(R.string.serving) + String.valueOf(recipe.getServing());
            serving.setText(servingText);

            container.setOnClickListener(view ->{
                listener.onClickRecipe(recipe);
            });
        }

        private void setImage(String url) {
            if (!url.equalsIgnoreCase("")) {
                Picasso.get().load(url)
                        .error(R.drawable.food)
                        .noPlaceholder()
                        .into(image);
            }
        }


    }

}
