package com.andrea.bakingapp.features.common.repository;

import android.support.annotation.NonNull;

import com.andrea.bakingapp.features.common.domain.Ingredient;
import com.google.gson.annotations.SerializedName;

class IngredientDto {

    @SerializedName("quantity") private float quantity;
    @SerializedName("measure") private String measure;
    @SerializedName("ingredient") private String ingredient;

    @NonNull Ingredient toIngredient() {
        return new Ingredient(quantity,
                              measure,
                              ingredient);
    }

}
