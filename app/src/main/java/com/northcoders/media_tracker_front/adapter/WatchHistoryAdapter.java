package com.northcoders.media_tracker_front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.model.WatchHistory;
import java.util.ArrayList;
import com.northcoders.media_tracker_front.databinding.WatchHistoryItemBinding;

public class WatchHistoryAdapter extends RecyclerView.Adapter<WatchHistoryAdapter.HistoryViewHolder> {
    ArrayList<WatchHistory> watchHistoryArrayList;
    Context context;
    RecyclerViewInterface recyclerViewInterface;

    public WatchHistoryAdapter(ArrayList<WatchHistory> watchList, Context context, RecyclerViewInterface recyclerViewInterface){
        this.watchHistoryArrayList = watchList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        WatchHistoryItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.watch_history_item,
                parent,
                false);
        return new HistoryViewHolder(binding, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position){
        WatchHistory watchHistory = watchHistoryArrayList.get(position);
        holder.watchHistoryItemBinding.setWatchHistory(watchHistory);
    }

    @Override
    public  int getItemCount(){
        return watchHistoryArrayList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        private static WatchHistoryItemBinding watchHistoryItemBinding;

        public HistoryViewHolder(WatchHistoryItemBinding binding, RecyclerViewInterface recyclerViewInterface) {
            super(binding.getRoot());
            this.watchHistoryItemBinding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                // Now to get the position of the chosen item from the recycler
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        // Get position of the adapter
                        int position = getAdapterPosition();
                        // Set the position to the interface
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}