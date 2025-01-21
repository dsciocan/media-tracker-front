package com.northcoders.media_tracker_front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FilmSearchResultItemBinding;
import com.northcoders.media_tracker_front.model.FilmSearchResult;

import java.util.ArrayList;

public class FilmSearchResultAdapter extends RecyclerView.Adapter<FilmSearchResultAdapter.FilmSearchResultViewHolder>{

        ArrayList<FilmSearchResult> showSearchResultArrayList;
        Context context;
        RecyclerViewInterface recyclerViewInterface;


    public FilmSearchResultAdapter(ArrayList<FilmSearchResult> showSearchResultArrayList, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.showSearchResultArrayList = showSearchResultArrayList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public FilmSearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilmSearchResultItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.film_search_result_item,
                parent,
                false);


        return new FilmSearchResultViewHolder(binding,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmSearchResultViewHolder holder, int position) {
        FilmSearchResult searchResult = showSearchResultArrayList.get(position);
        ImageView filmSearchPoster = holder.binding.filmSearchPoster;
        Glide.with(filmSearchPoster)
                .load(searchResult.getPoster_path())
                .error(R.drawable.no_poster)
                .into(filmSearchPoster);

        holder.binding.setFilmSearchResult(searchResult);
    }

    @Override
    public int getItemCount() {
        return showSearchResultArrayList.size();
    }


    public static class FilmSearchResultViewHolder extends RecyclerView.ViewHolder{
      private FilmSearchResultItemBinding binding;

      public FilmSearchResultViewHolder(FilmSearchResultItemBinding binding, RecyclerViewInterface recyclerViewInterface){
          super(binding.getRoot());
          this.binding = binding;

          itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(recyclerViewInterface != null){
                      int position = getAdapterPosition();
                      if(position != RecyclerView.NO_POSITION){
                          recyclerViewInterface.onItemClick(position);
                      }
                  }
              }
          });

      }

  }
}
