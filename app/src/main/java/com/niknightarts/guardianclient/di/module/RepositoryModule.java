package com.niknightarts.guardianclient.di.module;

import com.niknightarts.guardianclient.data.repository.NewsRepository;
import com.niknightarts.guardianclient.data.repository.NewsRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface RepositoryModule {
    @Binds
    NewsRepository provideNewsRepository(NewsRepositoryImpl implData);
}
