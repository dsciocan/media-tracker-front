package com.northcoders.media_tracker_front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.WatchingItemBinding;
import com.northcoders.media_tracker_front.model.UserEpisode;
import com.northcoders.media_tracker_front.model.UserShow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatchingAdapter extends RecyclerView.Adapter<WatchingAdapter.WatchingViewHolder> {

    List<UserShow> watchingList;
    Context context;
    RecyclerViewInterface recyclerView;
    private OnClickListener OnClickListener;

    public WatchingAdapter(List<UserShow> watchingList, Context context, RecyclerViewInterface recyclerView, Map<Long, UserEpisode> epWatchedByShow) {
        this.context = context;
        this.watchingList = watchingList;
        this.recyclerView = recyclerView;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.OnClickListener = listener;
    }


    @NonNull
    @Override
    public WatchingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WatchingItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.watching_item,
                parent,
                false);
        return new WatchingViewHolder(binding, recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchingViewHolder holder, int position) {
        UserShow watching = watchingList.get(holder.getAdapterPosition());
        WatchingViewHolder.watchingItemBinding.setWatching(watching);
        WatchingViewHolder.watchingItemBinding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickListener.onNextClick(v, position);
            }
        });
        WatchingViewHolder.watchingItemBinding.watchingItemInfo.setText("On EP " + (watching.getEpisodesWatched()+1));
        Glide.with(context).load(watching.getUserShowId().getShow().getPosterUrl()).into(WatchingViewHolder.watchingItemBinding.watchingItemImg);

    }

    @Override
    public int getItemCount() {
            return watchingList.size();
    }

    public static class WatchingViewHolder extends RecyclerView.ViewHolder {
        private static WatchingItemBinding watchingItemBinding;

        public WatchingViewHolder(WatchingItemBinding binding, RecyclerViewInterface recyclerView) {
            super(binding.getRoot());
            watchingItemBinding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerView != null) {
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION) {
                            recyclerView.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnClickListener {
        void onNextClick(View view, int position);
    }


}
