package com.andrea.bakingapp.features.common.repository;

import android.support.annotation.NonNull;

import com.andrea.bakingapp.features.common.domain.Recipe;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class RecipeRepositoryDefault implements RecipeRepository {

    private final RecipeDao recipeDao;

    @Inject
    RecipeRepositoryDefault(@NonNull RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    @NonNull
    @Override
    public Single<List<Recipe>> getRecipes() {
        return recipeDao.getRecipe().flatMap(list -> Observable.fromIterable(list)
                        .map(RecipeDto::toRecipe)
                        .toList());
    }
}
