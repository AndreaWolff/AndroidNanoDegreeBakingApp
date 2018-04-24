package com.andrea.bakingapp.dagger.component;

import android.content.Context;

import com.andrea.bakingapp.dagger.module.AppModule;
import com.andrea.bakingapp.dagger.module.NetModule;
import com.andrea.bakingapp.features.common.repository.RecipeRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {

    RecipeRepository getRecipeRepository();

    Context getContext();

}
