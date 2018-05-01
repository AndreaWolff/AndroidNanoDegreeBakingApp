package com.andrea.bakingapp.features.main.logic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.common.repository.RecipeRepository;
import com.andrea.bakingapp.features.details.ui.DetailsActivity;
import com.andrea.bakingapp.features.main.MainContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;

public class MainPresenter {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final RecipeRepository recipeRepository;
    private final Context context;

    private MainContract.View view;

    @Inject
    MainPresenter(@NonNull RecipeRepository recipeRepository,
                  @NonNull Context context) {
        this.recipeRepository = recipeRepository;
        this.context = context;
    }

    public void connectView(@Nullable MainContract.View view) {
        this.view = view;

        init();
    }

    private void init() {
        if (view != null) {
            view.renderScreenTitle(context.getString(R.string.main_title));
        }

        getRecipeList();
    }

    public void onViewDestroyed() {
        disposable.dispose();
        view = null;
    }

    private void getRecipeList() {
        if (view != null) {
            view.showLoadingIndicator();
        }

        disposable.add(recipeRepository.getRecipes()
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(this::handleRecipeResponseSuccessful, this::handleResponseError));
    }

    private void handleRecipeResponseSuccessful(List<Recipe> recipes) {
        if (view != null) {
            view.hideLoadingIndicator();
            view.showRecipeList(recipes);
        }
    }

    private void handleResponseError(Throwable throwable) {
        if (view != null) {
            view.hideLoadingIndicator();
            configureErrorMessage(throwable);
        }
    }

    public void onRecipeSelected(@NonNull Recipe recipe) {
        if (view != null) {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(RECIPE, recipe);
            view.navigateToRecipeDetails(intent);
        }
    }

    private void configureErrorMessage(Throwable throwable) {
        if (throwable.getMessage() == null) {
            Log.d(context.getString(R.string.error_title_caps), context.getString(R.string.error_no_message));
        } else {
            Log.d(context.getString(R.string.error_title_caps), throwable.getMessage());
        }

        if (view != null) {
            view.showError(context.getString(R.string.error_title), context.getString(R.string.error_message));
        }
    }
}