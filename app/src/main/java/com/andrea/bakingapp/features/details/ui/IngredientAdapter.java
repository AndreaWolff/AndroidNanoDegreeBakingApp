package com.andrea.bakingapp.features.details.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.features.common.domain.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andrea.bakingapp.util.ConversionUtil.convertToInteger;
import static com.andrea.bakingapp.util.ConversionUtil.convertToMeasurementName;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientsViewHolder> {

    private List<Ingredient> ingredients;

    IngredientAdapter(@NonNull List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return ingredients != null && ingredients.size() > 0 ? ingredients.size() : 0;
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredientTextView) TextView ingredientTextView;
        @BindView(R.id.quantityTextView) TextView quantityTextView;
        @BindView(R.id.measureTextView) TextView measureTextView;

        IngredientsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bind(int listItem) {
            ingredientTextView.setText(ingredients.get(listItem).getIngredient());

            float quantity = ingredients.get(listItem).getQuantity();
            if (quantity % 1 == 0) {
                quantityTextView.setText(String.valueOf(convertToInteger(quantity)));
            } else {
                quantityTextView.setText(String.valueOf(quantity));
            }

            measureTextView.setText(convertToMeasurementName(ingredients.get(listItem).getMeasure()));
        }
    }
}
