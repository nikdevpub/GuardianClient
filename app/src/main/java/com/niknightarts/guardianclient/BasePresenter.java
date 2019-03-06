package com.niknightarts.guardianclient;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends MvpView> extends MvpPresenter<V> {

    private CompositeDisposable disposables = new CompositeDisposable();

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    protected void clearDisposables() {
        disposables.clear();
    }

    @Override
    public void onDestroy() {
        clearDisposables();
        super.onDestroy();
    }
}
