package com.andrea.bakingapp.features.common.repository;

import android.support.annotation.NonNull;

import com.andrea.bakingapp.features.common.domain.Recipe;

import java.util.List;

import io.reactivex.Single;

public interface RecipeRepository {

    @NonNull Single<List<Recipe>> getRecipes();

}
