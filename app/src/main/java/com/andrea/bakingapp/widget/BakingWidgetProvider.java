package com.andrea.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.features.common.domain.Ingredient;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.details.ui.DetailsActivity;
import com.andrea.bakingapp.features.main.ui.MainActivity;
import com.google.gson.Gson;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Context.MODE_PRIVATE;
import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;
import static com.andrea.bakingapp.features.common.ActivityConstants.SHARED_PREFERENCES;
import static com.andrea.bakingapp.features.common.ActivityConstants.WIDGET;
import static com.andrea.bakingapp.util.ConversionUtil.convertToInteger;
import static com.andrea.bakingapp.util.ConversionUtil.convertToMeasurementName;

/**
 * This widget code was adapted and inspired by https://github.com/amanjeetsingh150/Baking-App/tree/master/app/src/main/java/com/developers/bakingapp/widget
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Recipe recipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_baking);

        Intent appIntent;

        if (recipe == null) {
            views.setTextViewText(R.id.recipeNameTextView, context.getString(R.string.widget_no_recipe));

            appIntent = new Intent(context, MainActivity.class);
        } else {
            views.setTextViewText(R.id.recipeNameTextView, recipe.getName());
            views.removeAllViews(R.id.widgetIngredientsLinearLayout);
            for (Ingredient ingredient : recipe.getIngredients()) {
                RemoteViews ingredientView = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_list_item);
                ingredientView.setTextViewText(R.id.ingredientNameTextView, "\u2022 " +
                                                                                    ingredient.getIngredient() + " - " +
                                                                                    String.valueOf(convertToInteger(ingredient.getQuantity())) + " " +
                                                                                    convertToMeasurementName(ingredient.getMeasure()));
                views.addView(R.id.widgetIngredientsLinearLayout, ingredientView);
            }

            appIntent = new Intent(context, DetailsActivity.class);
            appIntent.putExtra(RECIPE, recipe);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.recipeNameTextView, pendingIntent);
        views.setOnClickPendingIntent(R.id.widgetIngredientsLinearLayout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)  {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        Recipe recipe = new Gson().fromJson(sharedPreferences.getString(WIDGET, null), Recipe.class);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

