package com.niknightarts.guardianclient.presentation.allnews;

import com.arellomobile.mvp.InjectViewState;
import com.niknightarts.guardianclient.BasePresenter;
import com.niknightarts.guardianclient.data.entity.allnews.NewsResponse;
import com.niknightarts.guardianclient.data.repository.NewsRepository;
import com.niknightarts.guardianclient.ui.allnews.PaginationCallback;
import com.niknightarts.guardianclient.utils.Screens;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public final class NewsListPresenter extends BasePresenter<NewsListView> implements
                                                                         PaginationCallback {
    private final static int ITEMS_ON_PAGE = 10;
    private int currentPage = 1;
    private int totalPages = 1;
    private String queryText;
    private boolean isNextPageLoading;

    private final Router router;
    private final NewsRepository newsRepository;

    @Inject
    public NewsListPresenter(
            Router router,
            NewsRepository newsRepository
    ) {
        this.router = router;
        this.newsRepository = newsRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        onQueryTextSubmit("");
    }

    public void onQueryTextSubmit(String newText) {
        clearDisposables();
        currentPage = 1;
        totalPages = 1;
        isNextPageLoading = true;
        queryText = newText;
        newsRepository
                .getNewsList(queryText, currentPage, ITEMS_ON_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    getViewState().hideProgressBar();
                    isNextPageLoading = false;
                })
                .subscribe(new SingleObserver<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                        getViewState().showProgressBar();
                    }

                    @Override
                    public void onSuccess(NewsResponse newsResponse) {
                        totalPages = newsResponse.getResponse().getPages();
                        getViewState().showNews(newsResponse.getResponse().getNews());

                        if (!isLastPage()) getViewState().addLoadingFooter();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Timber.d(e);
                        getViewState().showError();
                    }
                });
    }

    public void openArticleDetails(String id) {
        router.navigateTo(new Screens.ArticleDetailsScreen(id));
    }

    @Override
    public void loadNextPage() {
        clearDisposables();
        currentPage++;
        isNextPageLoading = true;
        newsRepository
                .getNewsList(queryText, currentPage, ITEMS_ON_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> isNextPageLoading = false)
                .subscribe(new SingleObserver<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NewsResponse newsResponse) {
                        totalPages = newsResponse.getResponse().getPages();

                        getViewState().removeLoadingFooter();
                        getViewState().addNews(newsResponse.getResponse().getNews());
                        if (!isLastPage()) getViewState().addLoadingFooter();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public boolean isLastPage() {
        return currentPage >= totalPages;
    }

    @Override
    public boolean isNextPageLoading() {
        return isNextPageLoading;
    }
}
