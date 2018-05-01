package com.andrea.bakingapp.features.details.logic;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.bakingapp.widget.BakingWidgetProvider;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.details.DetailsContract;
import com.google.gson.Gson;

import javax.inject.Inject;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;
import static android.content.Context.MODE_PRIVATE;
import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;
import static com.andrea.bakingapp.features.common.ActivityConstants.SHARED_PREFERENCES;
import static com.andrea.bakingapp.features.common.ActivityConstants.WIDGET;

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
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putString(WIDGET, new Gson().toJson(recipe)).apply();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetId = new Bundle().getInt(EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);
        BakingWidgetProvider.updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
    }

}
