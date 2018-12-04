package com.demo1.ngtszwah.rssreader.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo1.ngtszwah.rssreader.R;
import com.demo1.ngtszwah.rssreader.interfaces.OnItemClickListener;
import com.demo1.ngtszwah.rssreader.models.Feed;
import com.demo1.ngtszwah.rssreader.models.FeedItem;
import com.squareup.picasso.Picasso;

public class FeedItemRecyclerViewAdapter extends RecyclerView.Adapter<FeedItemRecyclerViewAdapter.FeedItemViewHolder> {

    private Feed feed;
    private final OnItemClickListener listener;

    public FeedItemRecyclerViewAdapter(Feed feed, OnItemClickListener listener) {
        this.feed = feed;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FeedItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.feed_item_layout, viewGroup, false);

        FeedItemViewHolder viewHolder = new FeedItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedItemViewHolder feedItemViewHolder, int i) {

        feedItemViewHolder.viewHolderTitleTextView.setText(feed.getItems().get(i).getTitle());
        feedItemViewHolder.viewHolderDescriptionTextView.setText(feed.getItems().get(i).getDescription());
        feedItemViewHolder.viewHolderDateTimeTextView.setText(feed.getItems().get(i).getDateString());
        Picasso.get().load(feed.getItems().get(i).getImageUrl()).into(feedItemViewHolder.viewHolderImageView);
        feedItemViewHolder.bind(feed.getItems().get(i), listener);

    }

    @Override
    public int getItemCount() {
        return feed.getItems().size();
    }

    public class FeedItemViewHolder extends RecyclerView.ViewHolder{
        public TextView viewHolderTitleTextView;
        public TextView viewHolderDescriptionTextView;
        public TextView viewHolderDateTimeTextView;
        public ImageView viewHolderImageView;

        public FeedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            viewHolderTitleTextView = itemView.findViewById(R.id.title);
            viewHolderImageView = itemView.findViewById(R.id.thumbnail);
            viewHolderDescriptionTextView = itemView.findViewById(R.id.descriptionTextViewId);
            viewHolderDateTimeTextView = itemView.findViewById(R.id.dateTimeTextViewId);
        }

        public void bind(final FeedItem item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
            viewHolderImageView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
