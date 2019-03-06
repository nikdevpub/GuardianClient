package com.niknightarts.guardianclient.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.niknightarts.guardianclient.BasePresenter;
import com.niknightarts.guardianclient.utils.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public final class MainPresenter extends BasePresenter<MainView> {
    private final Router router;

    @Inject
    public MainPresenter(Router router) {
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        router.replaceScreen(new Screens.NewsListScreen());
    }
}
