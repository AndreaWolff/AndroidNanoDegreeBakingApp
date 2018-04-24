package com.andrea.bakingapp.dagger.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.andrea.bakingapp.application.BakingApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final BakingApplication application;

    public AppModule(@NonNull BakingApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context context() {
        return application.getApplicationContext();
    }

}
