package com.andrea.bakingapp.dagger.component;

import com.andrea.bakingapp.dagger.module.MainModule;
import com.andrea.bakingapp.dagger.scope.PerActivity;
import com.andrea.bakingapp.features.main.ui.MainFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {

    void inject(MainFragment fragment);

}
