package com.niknightarts.guardianclient.utils;

import android.support.v4.app.Fragment;

import com.niknightarts.guardianclient.ui.allnews.NewsListFragment;
import com.niknightarts.guardianclient.ui.article.ArticleDetailsFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public final class Screens {
    public static final class NewsListScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return NewsListFragment.newInstance();
        }
    }

    public static final class ArticleDetailsScreen extends SupportAppScreen {
        private final String id;

        public ArticleDetailsScreen(String id) {
            this.id = id;
        }

        @Override
        public Fragment getFragment() {
            return ArticleDetailsFragment.newInstance(id);
        }
    }
}
