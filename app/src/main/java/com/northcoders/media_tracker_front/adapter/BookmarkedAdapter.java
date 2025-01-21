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
import com.bumptech.glide.Glide;


import java.util.List;

public class BookmarkedAdapter extends RecyclerView.Adapter<BookmarkedAdapter.BookmarkedViewHolder> {

    List<Bookmarked> bookmarkedList;

    Context context;

    RecyclerViewInterface recyclerViewInterface;

    public BookmarkedAdapter(List<Bookmarked> bookmarkedList, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.bookmarkedList = bookmarkedList;
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

        Bookmarked bookmarked = bookmarkedList.get(position);
        BookmarkedViewHolder.bookmarkedItemBinding.setBookmarked(bookmarked);
        Glide.with(context).load(bookmarked.getUserFilmId().getFilm().getPosterUrl()).into(holder.bookmarkedItemBinding.bookmarkedFragmentImg);

    }

    @Override
    public int getItemCount() {
        if(bookmarkedList != null) {
            return bookmarkedList.size();
        } else {
            return 0;
        }
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
