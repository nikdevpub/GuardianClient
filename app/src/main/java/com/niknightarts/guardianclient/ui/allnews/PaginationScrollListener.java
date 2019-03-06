package com.niknightarts.guardianclient.ui.allnews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;
    PaginationCallback callback;

    public PaginationScrollListener(
            LinearLayoutManager layoutManager,
            PaginationCallback callback
    ) {
        this.layoutManager = layoutManager;
        this.callback = callback;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!callback.isNextPageLoading() && !callback.isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                callback.loadNextPage();
            }
        }
    }
}
