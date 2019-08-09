package com.bakingapp.android.udacitybakingapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private List<Ingredient> ingredients = new ArrayList<>();

    void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.clear();
        this.ingredients.add(new Ingredient()); //First dummy ingredient to be header
        this.ingredients.addAll(ingredients);
        notifyDataSetChanged();
    }


    private static final int HEADER_TYPE = 0;
    private static final int ROW_TYPE = 1;


    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == HEADER_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item_header, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        }
        return new IngredientsViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_TYPE;
        } else {
            return ROW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_list_name)
        TextView ingredientName;

        IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Ingredient ingredient) {
            if (super.getItemViewType() == ROW_TYPE) {
                String quantityText = String.valueOf(ingredient.getQuantity());
                String measureText = ingredient.getMeasure();
                String nameText = ingredient.getName();
                StringBuilder ingredientText = new StringBuilder()
                        .append("(")
                        .append(quantityText)
                        .append(" ")
                        .append(measureText)
                        .append(") ")
                        .append(nameText);

                ingredientName.setText(ingredientText);
            }
        }

    }

}
