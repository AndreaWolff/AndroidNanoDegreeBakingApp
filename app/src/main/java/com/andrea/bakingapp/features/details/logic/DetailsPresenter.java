package com.andrea.bakingapp.features.details.logic;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.details.DetailsContract;

import javax.inject.Inject;

import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;

public class DetailsPresenter {

    private DetailsContract.View view;
    private Recipe recipe;

    @Inject
    DetailsPresenter() {

    }

    public void connectView(@Nullable DetailsContract.View view, @Nullable Bundle savedInstanceState, @Nullable Bundle extras) {
        this.view = view;

        if (savedInstanceState == null) {
            if (extras == null) {
                assert view != null;
                view.finishScreen();
                return;
            }

            recipe = extras.getParcelable(RECIPE);
        } else {
            recipe = savedInstanceState.getParcelable(RECIPE);
        }

        init();
    }

    private void init() {
        if (view != null) {
            view.renderScreenTitle(recipe.getName());

            view.showIngredients(recipe.getIngredients());
            view.showSteps(recipe.getSteps(), recipe);
        }
    }

    public void onResume() {
//        EventBus.getDefault().register(this);
    }

    public void onPause() {
//        EventBus.getDefault().unregister(this);
    }

    public void onViewDestroyed() {
        view = null;
    }

//    @Subscribe
//    public void onEvent(StepEvent event) {
//        if (view != null) {
//            Intent intent = new Intent(context, InstructionActivity.class);
//            intent.putExtra(RECIPE, recipe);
//            intent.putExtra(STEP, event.getStep());
//            view.navigateToRecipeDetails(intent);
//        }
//    }

    public void onSavedInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE, recipe);
    }

}
