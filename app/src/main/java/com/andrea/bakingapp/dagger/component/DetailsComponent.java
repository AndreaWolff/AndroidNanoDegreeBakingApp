package com.andrea.bakingapp.dagger.component;

import com.andrea.bakingapp.dagger.module.DetailsModule;
import com.andrea.bakingapp.dagger.scope.PerActivity;
import com.andrea.bakingapp.features.details.ui.DetailsActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = DetailsModule.class)
public interface DetailsComponent {

    void inject(DetailsActivity activity);

}

