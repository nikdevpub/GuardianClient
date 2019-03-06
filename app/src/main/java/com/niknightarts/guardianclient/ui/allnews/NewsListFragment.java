package com.niknightarts.guardianclient.ui.allnews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.niknightarts.guardianclient.App;
import com.niknightarts.guardianclient.BaseFragment;
import com.niknightarts.guardianclient.R;
import com.niknightarts.guardianclient.data.entity.allnews.News;
import com.niknightarts.guardianclient.presentation.allnews.NewsListPresenter;
import com.niknightarts.guardianclient.presentation.allnews.NewsListView;
import com.niknightarts.guardianclient.utils.KeyboardUtil;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public final class NewsListFragment extends BaseFragment implements NewsListView {
    @Inject
    @InjectPresenter
    NewsListPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    @BindView(R.id.news_list_progress)
    ProgressBar progress;
    @BindView(R.id.news_list_recycler)
    RecyclerView newsRecycler;

    private NewsListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    NewsListPresenter providePresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.appComponent().inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    public View onCreateView(
            final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState
    ) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initRecycler();
    }

    private void initRecycler() {
        newsRecycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        newsRecycler.setLayoutManager(linearLayoutManager);
        newsRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsListAdapter(getContext(), id -> {
            KeyboardUtil.hideSoftKeyboard(Objects.requireNonNull(getActivity()));
            presenter.openArticleDetails(id);
        });
        newsRecycler.setAdapter(adapter);
        newsRecycler
                .addOnScrollListener(new PaginationScrollListener(linearLayoutManager, presenter));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.newslist, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_active);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                KeyboardUtil.hideSoftKeyboard(Objects.requireNonNull(getActivity()));
                presenter.onQueryTextSubmit(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setQueryHint(getString(R.string.search));

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initToolbar() {
        setToolbar(toolbar, false);
        toolbar.setTitle(R.string.app_name);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void showProgressBar() {
        newsRecycler.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        newsRecycler.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void addNews(List<News> news) {
        adapter.addAll(news);
    }

    @Override
    public void addLoadingFooter() {
        adapter.addLoadingFooter();
    }

    @Override
    public void removeLoadingFooter() {
        adapter.removeLoadingFooter();
    }

    @Override
    public void showError() {
        //todo: add error snackbar
    }

    @Override
    public void showNews(List<News> news) {
        adapter.setNews(news);
    }
}
