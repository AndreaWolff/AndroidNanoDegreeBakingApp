package com.andrea.bakingapp.features.details.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.bakingapp.features.common.domain.Ingredient;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.common.domain.Step;
import com.andrea.bakingapp.features.details.DetailsContract;
import com.andrea.bakingapp.features.details.ui.DetailsActivity;
import com.andrea.bakingapp.features.instruction.ui.InstructionActivity;

import java.util.List;

import javax.inject.Inject;

import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;
import static com.andrea.bakingapp.features.common.ActivityConstants.STEP;

public class DetailsPresenter {

    private final Context context;

    private DetailsContract.View view;
    private Recipe recipe;

    @Inject
    DetailsPresenter(@NonNull Context context) {
        this.context = context;
    }

    public void connectView(@Nullable DetailsContract.View view, @Nullable Bundle savedInstanceState, @Nullable Bundle extras) {
        this.view = view;

        if (extras == null) {
            assert view != null;
            view.finishScreen();
            return;
        }

        recipe = extras.getParcelable(RECIPE);

        init();
    }

    private void init() {
        if (view != null) {
            view.renderScreenTitle(recipe.getName());

            view.showIngredients(recipe.getIngredients());
            view.showSteps(recipe.getSteps());
        }
    }

    public void onViewDestroyed() {
        view = null;
    }

    public void onStepSelected(@NonNull Step step) {
        if (view != null) {
            Intent intent = new Intent(context, InstructionActivity.class);
            intent.putExtra(RECIPE, recipe);
            intent.putExtra(STEP, step);
            view.navigateToRecipeDetails(intent);
        }
    }
}
