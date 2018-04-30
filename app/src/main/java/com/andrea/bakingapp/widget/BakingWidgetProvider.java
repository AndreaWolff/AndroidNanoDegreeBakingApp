package com.andrea.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.features.common.ActivityConstants;
import com.andrea.bakingapp.features.common.domain.Ingredient;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.details.ui.DetailsActivity;
import com.andrea.bakingapp.features.main.ui.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;
import static com.andrea.bakingapp.util.ConversionUtil.convertToInteger;
import static com.andrea.bakingapp.util.ConversionUtil.convertToMeasurementName;

public class BakingWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Recipe recipe, List<Ingredient> ingredients) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        Intent appIntent;

        if (recipe == null) {
            views.setTextViewText(R.id.recipe_name_text_view, context.getString(R.string.widget_no_recipe));

            appIntent = new Intent(context, MainActivity.class);
        } else {
            views.setTextViewText(R.id.recipe_name_text_view, recipe.getName());
            views.removeAllViews(R.id.widget_ingredients_container);
            for (Ingredient ingredient : ingredients) {
                RemoteViews ingredientView = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_list_item);
                ingredientView.setTextViewText(R.id.ingredient_name_text_view, "\u2022 " +
                                                                                    ingredient.getIngredient() + " - " +
                                                                                    String.valueOf(convertToInteger(ingredient.getQuantity())) + " " +
                                                                                    convertToMeasurementName(ingredient.getMeasure()));
                views.addView(R.id.widget_ingredients_container, ingredientView);
            }

            appIntent = new Intent(context, DetailsActivity.class);
            appIntent.putExtra(RECIPE, recipe);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.recipe_name_text_view, pendingIntent);
        views.setOnClickPendingIntent(R.id.widget_ingredients_container, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)  {
        // This widget code was inspired by https://github.com/amanjeetsingh150/Baking-App/tree/master/app/src/main/java/com/developers/bakingapp/widget
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(ActivityConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Recipe recipe = new Gson().fromJson(sharedPreferences.getString(ActivityConstants.WIDGET_RESULT, null), Recipe.class);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe, recipe != null ? recipe.getIngredients() : new ArrayList<>());
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

