package com.niknightarts.guardianclient.ui.allnews;

public interface PaginationCallback {
    void loadNextPage();

    boolean isLastPage();

    boolean isNextPageLoading();
}
