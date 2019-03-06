package com.niknightarts.guardianclient.data.network;

import com.niknightarts.guardianclient.data.entity.article.ArticleResponse;
import com.niknightarts.guardianclient.data.entity.allnews.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GuardianApi {
    @GET("search?show-fields=thumbnail")
    Single<NewsResponse> getNewsList(
            @Query("q") String searchText,
            @Query("page") int currentPage,
            @Query("page-size") int pageSize
    );

    @GET("{id}?show-fields=headline,thumbnail,bodyText")
    Single<ArticleResponse> getArticle(@Path(value = "id", encoded = true) String id);
}
