package com.andrea.bakingapp.dagger.component;

import com.andrea.bakingapp.dagger.module.InstructionModule;
import com.andrea.bakingapp.dagger.scope.PerActivity;
import com.andrea.bakingapp.features.instruction.ui.InstructionFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = InstructionModule.class)
public interface InstructionComponent {

    void inject(InstructionFragment fragment);

}
