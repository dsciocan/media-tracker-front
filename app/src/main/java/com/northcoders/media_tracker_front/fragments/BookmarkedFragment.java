package com.northcoders.media_tracker_front.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.MainActivity;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.BookmarkedAdapter;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.databinding.FragmentBookmarkedBinding;
import com.northcoders.media_tracker_front.model.Bookmarked;
import com.northcoders.media_tracker_front.viewmodel.BookmarkedDetailsViewModel;
import com.northcoders.media_tracker_front.viewmodel.BookmarkedViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookmarkedFragment extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    List<Bookmarked> bookmarkedList;
    BookmarkedAdapter adapter;

    FragmentBookmarkedBinding binding;
    BookmarkedViewModel viewModel;
//    BookmarkedDetailsViewModel bookmarkedDetailsViewModel;

    ProfileFragment profileFragment = new ProfileFragment();

    public BookmarkedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookmarkedViewModel.class);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBookmarked();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarked, container, false);
        ImageButton profilePicture = binding.profilepicturemain ;
        Glide.with(profilePicture)
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                .circleCrop()
//                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.circularcustombutton)
                .into(profilePicture);


        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                android.R.anim.fade_in,
                                android.R.anim.fade_out,
                                android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right)
                        .replace(R.id.frameLayoutFragment, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return binding.getRoot();
    }

    private void getBookmarked() {
        viewModel.getBookmarked().observe(getViewLifecycleOwner(), new Observer<List<Bookmarked>>() {
            @Override
            public void onChanged(List<Bookmarked> list) {
                bookmarkedList = list;
                displayBookmarkedInRecyclerView();
            }
        });
    }

    private void displayBookmarkedInRecyclerView() {
        recyclerView = binding.bookmarkedRecyclerview;
        adapter = new BookmarkedAdapter(bookmarkedList, this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        Long id = bookmarkedList.get(position).getUserFilmId().getFilm().getId();
        BookmarkedDetailsFragment bookmarkedDetailsFragment = new BookmarkedDetailsFragment().newInstance(id);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutFragment,bookmarkedDetailsFragment)
                .addToBackStack("BookmarkedTransaction")
                .commit();
//        transaction.replace(R.id.frameLayoutFragment, BookmarkedDetailsFragment.class, null);
//        transaction.commit();
    }
}