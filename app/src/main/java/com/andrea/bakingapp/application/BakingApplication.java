package com.andrea.bakingapp.application;

import android.app.Application;

import com.andrea.bakingapp.dagger.component.AppComponent;
import com.andrea.bakingapp.dagger.component.DaggerAppComponent;
import com.andrea.bakingapp.dagger.module.AppModule;
import com.andrea.bakingapp.dagger.module.NetModule;
import com.facebook.stetho.Stetho;

public class BakingApplication extends Application {

    private static BakingApplication application;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        appComponent = createDaggerComponent();

        Stetho.initializeWithDefaults(this);
    }

    private AppComponent createDaggerComponent() {
        return DaggerAppComponent.builder()
                                 .appModule(new AppModule(this))
                                 .netModule(new NetModule("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/"))
                                 .build();
    }

    public static AppComponent getDagger() {
        return application.appComponent;
    }

}
