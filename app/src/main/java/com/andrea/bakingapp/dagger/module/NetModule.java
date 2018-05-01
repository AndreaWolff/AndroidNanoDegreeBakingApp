package com.andrea.bakingapp.dagger.module;

import android.support.annotation.NonNull;

import com.andrea.bakingapp.features.common.repository.RecipeDao;
import com.andrea.bakingapp.features.common.repository.RecipeRepository;
import com.andrea.bakingapp.features.common.repository.RecipeRepositoryDefault;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private final String BASE_URL;

    public NetModule(@NonNull String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    @Singleton
    @Provides
    OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                               .addNetworkInterceptor(new StethoInterceptor())
                               .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                               .build();
    }

    @Singleton
    @Provides
    Retrofit retrofit(@NonNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                           .client(okHttpClient)
                           .addConverterFactory(GsonConverterFactory.create())
                           .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                           .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                           .baseUrl(BASE_URL)
                           .build();
    }

    @Singleton
    @Provides
    RecipeDao recipeDao(Retrofit retrofit) {
        return retrofit.create(RecipeDao.class);
    }

    @Singleton
    @Provides
    RecipeRepository recipeRepository(RecipeRepositoryDefault impl) {
        return impl;
    }

}
