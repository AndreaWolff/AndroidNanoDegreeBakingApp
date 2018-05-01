package com.andrea.bakingapp.features.details;

import android.support.annotation.NonNull;

import com.andrea.bakingapp.features.common.domain.Ingredient;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.common.domain.Step;

import java.util.List;

public interface DetailsContract {

    interface View {

        void renderScreenTitle(@NonNull String title);

        void showIngredients(@NonNull List<Ingredient> ingredients);

        void showSteps(@NonNull List<Step> steps, @NonNull Recipe recipe);

        void finishScreen();

    }

}
