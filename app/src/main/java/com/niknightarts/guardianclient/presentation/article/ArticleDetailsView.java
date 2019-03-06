package com.niknightarts.guardianclient.presentation.article;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.niknightarts.guardianclient.data.entity.article.Article;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ArticleDetailsView extends MvpView {

    void showArticle(Article article);

    void showProgressBar();

    void hideProgressBar();
}
