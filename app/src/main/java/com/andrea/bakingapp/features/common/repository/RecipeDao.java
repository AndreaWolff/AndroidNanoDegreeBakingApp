package com.andrea.bakingapp.features.common.repository;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RecipeDao {

    @GET("baking.json") Single<List<RecipeDto>> getRecipe();

}
