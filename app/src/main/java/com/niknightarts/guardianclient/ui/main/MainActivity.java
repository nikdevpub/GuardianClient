package com.niknightarts.guardianclient.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.niknightarts.guardianclient.App;
import com.niknightarts.guardianclient.BaseActivity;
import com.niknightarts.guardianclient.R;
import com.niknightarts.guardianclient.presentation.main.MainPresenter;
import com.niknightarts.guardianclient.presentation.main.MainView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;


public final class MainActivity extends BaseActivity implements MainView {
    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    @InjectPresenter
    MainPresenter presenter;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return presenter;
    }

    public static Intent newIntent(final Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.appComponent().inject(this);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(new Navigator());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.handleOnBackPressed(R.id.main_container);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private final class Navigator extends SupportAppNavigator {
        Navigator() {
            super(MainActivity.this, R.id.main_container);
        }
    }
}
