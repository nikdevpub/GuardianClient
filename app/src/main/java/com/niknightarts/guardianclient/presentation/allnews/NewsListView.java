package com.niknightarts.guardianclient.presentation.allnews;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.niknightarts.guardianclient.data.entity.allnews.News;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface NewsListView extends MvpView {
    void showProgressBar();

    void hideProgressBar();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void addNews(List<News> news);

    void addLoadingFooter();

    void removeLoadingFooter();

    void showError();

    void showNews(List<News> news);
}
