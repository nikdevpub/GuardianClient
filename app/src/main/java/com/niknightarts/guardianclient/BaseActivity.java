package com.niknightarts.guardianclient;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.niknightarts.guardianclient.utils.BackButtonListener;


public abstract class BaseActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();

    protected void handleOnBackPressed(@IdRes int containerId) {
        Fragment fragment =
                getSupportFragmentManager().findFragmentById(containerId);

        if (!checkFragmentOnBackPressed(fragment)) {
            super.onBackPressed();
        }
    }

    protected boolean checkFragmentOnBackPressed(Fragment fragment) {
        return fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed();
    }
}
