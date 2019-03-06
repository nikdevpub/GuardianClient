package com.niknightarts.guardianclient.presentation.article;

import javax.inject.Inject;

import com.arellomobile.mvp.InjectViewState;
import com.niknightarts.guardianclient.BasePresenter;
import com.niknightarts.guardianclient.data.entity.article.Article;
import com.niknightarts.guardianclient.data.repository.NewsRepositoryImpl;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public final class ArticleDetailsPresenter extends BasePresenter<ArticleDetailsView> {
    private final Router router;
    private final NewsRepositoryImpl repository;
    private String id;

    @Inject
    public ArticleDetailsPresenter(
            Router router,
            NewsRepositoryImpl repository
    ) {
        this.router = router;
        this.repository = repository;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Timber.d(id);
        repository.getArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> getViewState().hideProgressBar())
                .subscribe(new SingleObserver<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getViewState().showProgressBar();
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(Article article) {
                        getViewState().showArticle(article);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Timber.d(e);
                    }
                });
    }

    public void goBack() {
        router.exit();
    }
}
