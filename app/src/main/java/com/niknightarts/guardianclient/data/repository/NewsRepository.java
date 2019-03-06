package com.niknightarts.guardianclient.data.repository;

import com.niknightarts.guardianclient.data.entity.article.Article;
import com.niknightarts.guardianclient.data.entity.allnews.NewsResponse;

import io.reactivex.Single;

public interface NewsRepository {
    Single<NewsResponse> getNewsList(String searchText, int currentPage, int pageSize);

    Single<Article> getArticle(String id);
}
