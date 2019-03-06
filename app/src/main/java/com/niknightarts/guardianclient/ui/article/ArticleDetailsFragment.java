package com.niknightarts.guardianclient.ui.article;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.niknightarts.guardianclient.App;
import com.niknightarts.guardianclient.BaseFragment;
import com.niknightarts.guardianclient.R;
import com.niknightarts.guardianclient.data.entity.article.Article;
import com.niknightarts.guardianclient.presentation.article.ArticleDetailsPresenter;
import com.niknightarts.guardianclient.presentation.article.ArticleDetailsView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public final class ArticleDetailsFragment extends BaseFragment implements ArticleDetailsView {
    private final static String BUNDLE_ID = "ID";
    @Inject
    @InjectPresenter
    ArticleDetailsPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    @BindView(R.id.article_details_image)
    ImageView articleImage;
    @BindView(R.id.article_details_content_title)
    TextView contentTitle;
    @BindView(R.id.article_details_content_text)
    TextView contentText;
    @BindView(R.id.article_details_content)
    LinearLayout content;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static ArticleDetailsFragment newInstance(String id) {
        ArticleDetailsFragment fragment = new ArticleDetailsFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    ArticleDetailsPresenter providePresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.appComponent().inject(this);
        super.onCreate(savedInstanceState);
        presenter.setId(getArguments().getString(BUNDLE_ID));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_details;
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
    }

    private void initToolbar() {
        setToolbar(toolbar, true);
        toolbar.setTitle(R.string.article_details_toolbar_title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> presenter.goBack());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onBackPressed() {
        presenter.goBack();
        return true;
    }

    @Override
    public void showArticle(Article article) {
        contentTitle.setText(article.getWebTitle());
        contentText.setText(article.getFields().getBodyText());
        if (article.getFields().getThumbnail() == null) {
            articleImage.setVisibility(View.GONE);
        } else {
            articleImage.setVisibility(View.VISIBLE);
            Picasso.get().load(article.getFields().getThumbnail()).centerCrop().fit()
                    .into(articleImage);
        }
    }

    @Override
    public void showProgressBar() {
        content.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        content.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
