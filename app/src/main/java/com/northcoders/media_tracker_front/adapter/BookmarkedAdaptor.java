package com.northcoders.media_tracker_front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.BookmarkedItemBinding;
import com.northcoders.media_tracker_front.model.Bookmarked;

import java.util.ArrayList;

public class BookmarkedAdaptor extends RecyclerView.Adapter<BookmarkedAdaptor.BookmarkedViewHolder> {

    ArrayList<Bookmarked> bookmarkedArrayList;

    Context context;

    RecyclerViewInterface recyclerViewInterface;

    public BookmarkedAdaptor(ArrayList<Bookmarked> bookmarkedArrayList, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.bookmarkedArrayList = bookmarkedArrayList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public BookmarkedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookmarkedItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.bookmarked_item,
                parent,
                false);
        return new BookmarkedViewHolder(binding, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkedViewHolder holder, int position) {
        Bookmarked bookmarked = bookmarkedArrayList.get(position);
        BookmarkedViewHolder.bookmarkedItemBinding.setBookmarked(bookmarked);
    }

    @Override
    public int getItemCount() {
        return bookmarkedArrayList.size();
    }

    public static class BookmarkedViewHolder extends RecyclerView.ViewHolder {

        private static BookmarkedItemBinding bookmarkedItemBinding;

        public BookmarkedViewHolder(BookmarkedItemBinding binding, RecyclerViewInterface recyclerViewInterface) {
            super(binding.getRoot());
            bookmarkedItemBinding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        // Get position of the adapter
                        int position = getAdapterPosition();
                        // Set the position to the interface
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
