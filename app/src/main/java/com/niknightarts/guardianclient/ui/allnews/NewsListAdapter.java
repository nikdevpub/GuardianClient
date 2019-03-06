package com.niknightarts.guardianclient.ui.allnews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.niknightarts.guardianclient.R;
import com.niknightarts.guardianclient.data.entity.allnews.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int PROGRESS = 1;
    private boolean isLoadingAdded = false;

    private final Context сontext;
    private final OnNewsClickCallback callback;
    private List<News> models;

    public NewsListAdapter(Context сontext, OnNewsClickCallback itemClickCallback) {
        this.сontext = сontext;
        this.callback = itemClickCallback;
        models = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                return new ItemViewHolder(
                        inflater.inflate(R.layout.item_news, parent, false));
            case PROGRESS:
                return new ProgressViewHolder(
                        inflater.inflate(R.layout.item_news_progress, parent, false));
            default:
                return null;
        }
    }

    public void setNews(List<News> list) {
        models.clear();
        models.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        News model = models.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                ItemViewHolder itemHolder = (ItemViewHolder) holder;
                itemHolder.date.setText(model.getCustomPublicationDate());
                itemHolder.section.setText(model.getSectionName());
                itemHolder.title.setText(model.getWebTitle());
                Timber.d(model.getId());
                itemHolder.itemView.setOnClickListener(v -> callback.onNewsClick(model.getId()));

                if (model.getFields() == null) {
                    Picasso.get().load(R.drawable.ic_priority_high_black_24dp)
                            .placeholder(R.drawable.ic_priority_high_black_24dp)
                            .into(itemHolder.image);
                } else {
                    Picasso.get().load(model.getFields().getThumbnail())
                            .placeholder(R.drawable.ic_priority_high_black_24dp)
                            .error(R.drawable.ic_priority_high_black_24dp).centerCrop().fit()
                            .into(itemHolder.image);
                }
                break;
            case PROGRESS:
                holder.itemView.setOnClickListener(null);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (models == null || models.isEmpty()) return 0;
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == models.size() - 1 && isLoadingAdded) ? PROGRESS : ITEM;
    }

    public interface OnNewsClickCallback {
        void onNewsClick(String id);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_news_image)
        ImageView image;
        @BindView(R.id.item_news_title)
        TextView title;
        @BindView(R.id.item_news_date)
        TextView date;
        @BindView(R.id.item_news_section)
        TextView section;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    protected class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * Helpers
     */

    public void add(News news) {
        models.add(news);
        notifyItemInserted(models.size() - 1);
    }

    public void addAll(List<News> models) {
        for (News news : models) {
            add(news);
        }
    }

    public void remove(News payment) {
        int position = models.indexOf(payment);
        if (position > -1) {
            models.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new News());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = models.size() - 1;
        News news = getItem(position);

        if (news != null) {
            models.remove(position);
            notifyItemRemoved(position);
        }
    }

    public News getItem(int position) {
        return models.get(position);
    }
}
