package com.niknightarts.guardianclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.niknightarts.guardianclient.utils.BackButtonListener;

public abstract class BaseFragment extends MvpAppCompatFragment
        implements BackButtonListener {
    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    protected void setToolbar(Toolbar toolbar, boolean hasNavigation) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        if (hasNavigation) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
