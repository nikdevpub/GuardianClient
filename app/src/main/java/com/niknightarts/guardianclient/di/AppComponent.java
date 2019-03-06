package com.niknightarts.guardianclient.di;

import com.niknightarts.guardianclient.di.module.ApiModule;
import com.niknightarts.guardianclient.di.module.ContextModule;
import com.niknightarts.guardianclient.di.module.NavigationModule;
import com.niknightarts.guardianclient.di.module.RepositoryModule;
import com.niknightarts.guardianclient.ui.article.ArticleDetailsFragment;
import com.niknightarts.guardianclient.ui.main.MainActivity;
import com.niknightarts.guardianclient.ui.allnews.NewsListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ContextModule.class,
                NavigationModule.class,
                RepositoryModule.class,
                ApiModule.class
        }
)
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(NewsListFragment newsListFragment);

    void inject(ArticleDetailsFragment articleDetailsFragment);
}
