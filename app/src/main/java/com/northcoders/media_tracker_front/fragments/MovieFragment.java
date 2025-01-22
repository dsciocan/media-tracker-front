package com.northcoders.media_tracker_front.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentMovieBinding;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.viewmodel.MovieDetailsViewModel;

public class MovieFragment extends Fragment {
    private static final String MOVIE_ID_KEY = "MovieKey"  ;
    FilmDetails currentFilmDetails = new FilmDetails();
    MovieDetailsViewModel viewModel;
    private ProfileFragment profileFragment = new ProfileFragment();
    FragmentMovieBinding binding;

    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(long id){
        MovieFragment movieFragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(MOVIE_ID_KEY,id);
        Log.i("Movie Fragment", String.valueOf(id));
        movieFragment.setArguments(bundle);
        return movieFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Long key = getArguments().getLong(MOVIE_ID_KEY);
            /*currentFilmDetails = viewModel.getFilmDetails(key);*/
            getFilmDetails(key);
        }

        setSwitchLogic();

    }

    public void setSwitchLogic(){
        binding.movieStatusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchAlert();
                    binding.movieStatusSwitch.setChecked(true);
                }
            }
        });


    }

    private void switchAlert(){
        AlertDialog.Builder quit = new AlertDialog.Builder(getContext())
                .setTitle("Add To Bookmark")
                .setMessage("Do you want to add this to the you 'Bookmark' list? ")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.saveUserFilm(getArguments().getLong(MOVIE_ID_KEY));
                        Toast.makeText(getContext(), "Successfully Added To Bookmarked", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        quit.show();



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container, false);
        return binding.getRoot();
    }



    public void getFilmDetails(Long id){
        viewModel.getFilmDetails(id).observe(getViewLifecycleOwner(), new Observer<FilmDetails>() {
            @Override
            public void onChanged(FilmDetails filmDetails) {
                currentFilmDetails = (FilmDetails) filmDetails;
                bindContent(currentFilmDetails);
            }
        });
    }

    public void bindContent(FilmDetails filmDetails){
        binding.runtime.setText(currentFilmDetails.getRuntime() + " mins");
        binding.language.setText(currentFilmDetails.getOriginal_language());
        binding.date.setText(currentFilmDetails.getRelease_date());
        binding.title.setText(currentFilmDetails.getTitle());
        binding.overview.setText(currentFilmDetails.getOverview());
        binding.language.setText("Lang: " +  currentFilmDetails.getOriginal_language());
//        List<String> genres = currentFilmDetails.getGenres();
//        binding.genres.setText(String.join(", ", genres));
        Glide.with(binding.poster.getContext())
                .load(currentFilmDetails.getPoster_path())
                .into(binding.poster);
        ImageButton profilePicture = binding.avatar ;
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
                        .addToBackStack("profileFragmentTransaction") // allow user to press back to go back to home fragment
                        .commit();
            }
        });

//        CheckBox checkBox = binding.bookmark;
//        checkBox.setOnCheckedChangeListener();

    }



}