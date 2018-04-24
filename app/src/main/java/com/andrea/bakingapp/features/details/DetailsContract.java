package com.andrea.bakingapp.features.details;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.andrea.bakingapp.features.common.domain.Ingredient;
import com.andrea.bakingapp.features.common.domain.Step;

import java.util.List;

public interface DetailsContract {
    interface View {
        void renderScreenTitle(@NonNull String name);

        void showIngredients(@NonNull List<Ingredient> ingredients);

        void showSteps(@NonNull List<Step> steps);

        void finishScreen();

        void navigateToRecipeDetails(@NonNull Intent intent);
    }
}
