package com.andrea.bakingapp.features.instruction.logic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.common.domain.Step;
import com.andrea.bakingapp.features.instruction.InstructionContract;

import javax.inject.Inject;

import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;
import static com.andrea.bakingapp.features.common.ActivityConstants.STEP;

public class InstructionPresenter {

    private final Context context;

    private InstructionContract.View view;
    private Recipe recipe;
    private Step step;

    @Inject
    InstructionPresenter(@NonNull Context context) {
        this.context = context;
    }

    public void connectView(@Nullable InstructionContract.View view, @Nullable Bundle savedInstanceState, @Nullable Bundle extras) {
        this.view = view;

        if (extras == null) {
            assert view != null;
            view.finish();
            return;
        }

        recipe = extras.getParcelable(RECIPE);
        step = extras.getParcelable(STEP);

        init();
    }

    private void init() {
        if (view != null) {
            view.renderScreenTitle(recipe.getName());

            String shortDescription = step.getShortDescription();
            String description = step.getDescription();
            view.showRecipeInstructions(step.getShortDescription(), step.getDescription());
        }
    }

}
