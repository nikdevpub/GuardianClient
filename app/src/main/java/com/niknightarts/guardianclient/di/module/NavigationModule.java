package com.niknightarts.guardianclient.di.module;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class NavigationModule {
    private Cicerone<Router> cicerone = Cicerone.create();

    @Provides
    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    @Provides
    public Router getRouter() {
        return cicerone.getRouter();
    }
}
