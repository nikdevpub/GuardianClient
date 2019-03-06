package com.niknightarts.guardianclient.data.repository;

import com.niknightarts.guardianclient.data.entity.article.Article;
import com.niknightarts.guardianclient.data.entity.allnews.NewsResponse;
import com.niknightarts.guardianclient.data.network.RetrofitClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

public final class NewsRepositoryImpl implements NewsRepository {
    private final RetrofitClient retrofitClient;

    @Inject
    public NewsRepositoryImpl(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
    }

    @Override
    public Single<NewsResponse> getNewsList(String searchText, int currentPage, int pageSize) {
        return retrofitClient
                .getGuardianApi()
                .getNewsList(searchText, currentPage, pageSize)
                .retryWhen(throwable -> {
                    Timber.d("getNewsList() error");
                    return throwable.delay(2, TimeUnit.SECONDS);
                });
    }

    @Override
    public Single<Article> getArticle(String id) {
        return retrofitClient
                .getGuardianApi()
                .getArticle(id)
                .retryWhen(throwable -> {
                    Timber.d("getArticle() error");
                    return throwable.delay(2, TimeUnit.SECONDS);
                })
                .map(articleResponse -> articleResponse
                        .getResponse()
                        .getArticle());
    }
}

