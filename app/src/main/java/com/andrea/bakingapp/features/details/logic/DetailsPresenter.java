package com.andrea.bakingapp.features.details.logic;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.bakingapp.widget.BakingWidgetProvider;
import com.andrea.bakingapp.features.common.ActivityConstants;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.details.DetailsContract;
import com.google.gson.Gson;

import javax.inject.Inject;

import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;

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
            configureWidget();
        }
    }

    public void onViewDestroyed() {
        view = null;
    }

    public void onSavedInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE, recipe);
    }

    private void configureWidget() {
        // This widget code was inspired by https://github.com/amanjeetsingh150/Baking-App
        SharedPreferences sharedPreferences = context.getSharedPreferences(ActivityConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String json = new Gson().toJson(recipe);
        sharedPreferences.edit().putString(ActivityConstants.WIDGET_RESULT, json).apply();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetId = new Bundle().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        BakingWidgetProvider.updateAppWidget(context, appWidgetManager, appWidgetId, recipe, recipe.getIngredients());
    }

}
