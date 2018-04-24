package com.andrea.bakingapp.features.common.repository;

import android.support.annotation.NonNull;

import com.andrea.bakingapp.features.common.domain.Ingredient;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.common.domain.Step;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class RecipeDto {

    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("ingredients") private List<IngredientDto> ingredientsDtos;
    @SerializedName("steps") private List<StepDto> stepDtos;
    @SerializedName("servings") private int servings;
    @SerializedName("image") private String image;

    @NonNull Recipe toRecipe() {
        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientDto dto : ingredientsDtos) {
            ingredients.add(dto.toIngredient());
        }

        List<Step> steps = new ArrayList<>();
        for (StepDto dto : stepDtos) {
            steps.add(dto.toStep());
        }

        return new Recipe(id,
                          name,
                          ingredients,
                          steps,
                          servings,
                          image);
    }
}