package com.northcoders.media_tracker_front.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.BookmarkedAdapter;
import com.northcoders.media_tracker_front.databinding.FragmentBookmarkedDetailsBinding;
import com.northcoders.media_tracker_front.model.Bookmarked;
import com.northcoders.media_tracker_front.viewmodel.BookmarkedDetailsViewModel;
import com.northcoders.media_tracker_front.viewmodel.BookmarkedViewModel;

import org.jetbrains.annotations.Nullable;


public class BookmarkedDetailsFragment extends Fragment {
    BookmarkedDetailsViewModel bookmarkedDetailsViewModel;
   Bookmarked bookmarkedFilm = new Bookmarked();
   FragmentBookmarkedDetailsBinding binding;
   ProfileFragment profileFragment = new ProfileFragment();
   BookmarkedFragment bookmarkedFragment = new BookmarkedFragment();
    private static final String MOVIE_ID_KEY = "movieKey"  ;


    public BookmarkedDetailsFragment() {
        // Required empty public constructor
    }


    public static BookmarkedDetailsFragment newInstance(Long id){
        BookmarkedDetailsFragment movieFragment = new BookmarkedDetailsFragment();
        Log.i("INSTANCE", String.valueOf(id));
        Bundle bundle = new Bundle();
        bundle.putLong(MOVIE_ID_KEY,id);
        movieFragment.setArguments(bundle);
        return movieFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookmarkedDetailsViewModel = new ViewModelProvider(this).get(BookmarkedDetailsViewModel.class);
    }

        @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Long filmId = getArguments().getLong("movieKey");
            getFilmDetails(filmId);
        }

        binding.bookmarkedFragmentBackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayoutFragment, bookmarkedFragment)
                            .addToBackStack("BookmarkedDetailsTransaction")
                            .commit();
            }
        });

            ImageButton profilePicture = binding.profilepicturebookmarked;
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
                            .addToBackStack("BookmarkedDetailsTransaction")
                            .commit();
                }
            });
    }

    public void getFilmDetails(Long id) {
        bookmarkedDetailsViewModel.getUserFilm(id).observe(getViewLifecycleOwner(), new Observer<Bookmarked>() {
            @Override
            public void onChanged(Bookmarked bookmarked) {
                bookmarkedFilm = bookmarked;
                setBindingText(bookmarkedFilm);
            }
        });
    }

    public void setBindingText(Bookmarked bookmarked) {
        Log.i("BOOKMARKEDDETAILSFRAGMENT", bookmarked.getUserFilmId().getFilm().getTitle());
        binding.setBookmarked(bookmarked);
        binding.bookmarkedFragmentRatingBar.setRating(bookmarked.getRating());
//        binding.bookmarkedFragmentTitle.setText(bookmarked.getUserFilmId().getFilm().getTitle());
        Glide.with(this).load(bookmarked.getUserFilmId().getFilm().getPoster_url()).into(binding.bookmarkedFragmentImage);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarked_details, container, false);
        // Inflate the layout for this fragment
    return binding.getRoot();

    }

}