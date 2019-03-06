package com.niknightarts.guardianclient.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Application application;

    public ContextModule(Application application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return application;
    }
}
