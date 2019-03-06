package com.niknightarts.guardianclient.di.module;

import com.niknightarts.guardianclient.data.network.GuardianApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {
    @Provides
    @Singleton
    GuardianApi provideApi(Retrofit retrofit){
        return retrofit.create(GuardianApi.class);
    }
}
