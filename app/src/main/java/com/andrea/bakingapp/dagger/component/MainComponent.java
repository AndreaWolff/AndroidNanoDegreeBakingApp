package com.andrea.bakingapp.dagger.component;

import com.andrea.bakingapp.features.main.ui.MainActivity;
import com.andrea.bakingapp.dagger.module.MainModule;
import com.andrea.bakingapp.dagger.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity activity);

}
