package com.northcoders.media_tracker_front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.model.WatchHistory;
import java.util.ArrayList;
import com.northcoders.media_tracker_front.databinding.WatchHistoryItemBinding;

public class ShowSearchResultAdapter extends RecyclerView.Adapter<ShowSearchResultAdapter.ShowSearchResultViewHolder> {
    ArrayList<ShowSearchResult> showSearchResultArrayList;
    Context context;
    RecyclerViewInterface recyclerViewInterface;

    public ShowSearchResultAdapter(ArrayList<ShowSearchResult> watchList, Context context, RecyclerViewInterface recyclerViewInterface){
        this.showSearchResultArrayList = watchList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ShowSearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        ShowSearchResultItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.show_search_result_item,
                parent,
                false);
        return new ShowSearchResultViewHolder(binding, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowSearchResultAdapter.ShowSearchResultViewHolder holder, int position){
        ShowSearchResult showSearchResult = showSearchResultArrayList.get(position);
        holder.showSearchResultItemBinding.setShowSearchResult(showSearchResult);
    }

    @Override
    public  int getItemCount(){
        return showSearchResultArrayList.size();
    }

    public static class ShowSearchResultViewHolder extends RecyclerView.ViewHolder{
        private static ShowSearchResultItemBinding showSearchResultItemBinding;

        public ShowSearchResultViewHolder(WatchHistoryItemBinding binding, RecyclerViewInterface recyclerViewInterface) {
            super(binding.getRoot());
            this.showSearchResultItemBinding = binding;

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
}
