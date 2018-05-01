package com.andrea.bakingapp.features.main;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.andrea.bakingapp.features.common.domain.Recipe;

import java.util.List;

public interface MainContract {
    interface View {
        void showRecipeList(@NonNull List<Recipe> recipes);

        void showError(@NonNull String errorTitle, @NonNull String errorMessage);

        void renderScreenTitle(@NonNull String title);

        void navigateToRecipeDetails(@NonNull Intent intent);

        void showLoadingIndicator();

        void hideLoadingIndicator();
    }
}
