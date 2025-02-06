package com.northcoders.media_tracker_front.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Rating;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentMovieBinding;
import com.northcoders.media_tracker_front.model.Film;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.viewmodel.BookmarkedDetailsViewModel;
import com.northcoders.media_tracker_front.viewmodel.MovieDetailsViewModel;

import java.time.LocalDate;
import java.util.Objects;

public class MovieFragment extends Fragment {
    private static final String MOVIE_ID_KEY = "MovieKey"  ;
    FilmDetails currentFilmDetails = new FilmDetails();
    MovieDetailsViewModel viewModel;
    private ProfileFragment profileFragment = new ProfileFragment();
    FragmentMovieBinding binding;
    UserFilm userFilm = new UserFilm();
    Film film = new Film();
    Boolean isSaved = false;

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
            isFilmSaved(key);
            getFilm(key);
            getUserFilm(film.getId());
        }
        backButtonLogic();
        handleFilmStatusLogic();
        setSwitchLogic();

    }

    public void setSwitchLogic(){
        binding.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFilmStatus().equals("WATCHED")) {
                    Toast.makeText(getContext(), "Film already watched", Toast.LENGTH_SHORT).show();
                    binding.bookmark.setChecked(false);
                } else if (checkFilmStatus().equals("BOOKMARKED") && binding.bookmark.isChecked()) {
                    Toast.makeText(getContext(), "Film already bookmarked", Toast.LENGTH_SHORT).show();
                    binding.bookmark.setChecked(true);
                } else if(binding.bookmark.isChecked()) {
                    switchAlert();
                } else {
                    removeAlert();
                }
            }
        });
    }

    public void removeAlert() {
        AlertDialog.Builder quit = new AlertDialog.Builder(getContext())
                .setTitle("Remove From Bookmarks")
                .setMessage("Do you want to remove this from your 'Bookmarked' list? ")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFilm(getArguments().getLong(MOVIE_ID_KEY));
                        Film savedFilm = film;
                        viewModel.deleteUserFilm(savedFilm.getId());
                        Toast.makeText(getContext(), "Successfully Removed From Bookmarked", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.bookmark.setChecked(true);
                        dialog.cancel();
                    }
                });
        quit.show();


    }

    private void getUserFilm(Long id) {
        viewModel.getUserFilm(id).observe(getViewLifecycleOwner(), new Observer<UserFilm>() {
            @Override
            public void onChanged(UserFilm newFilm) {
                userFilm = newFilm;
            }
        });
    }

    private void getFilm(Long tmdbId) {
        viewModel.getFilmDetailsByTmdbId(tmdbId).observe(getViewLifecycleOwner(), new Observer<Film>() {
                    @Override
                    public void onChanged(Film newFilm) {
                       film = newFilm;
                       getUserFilm(film.getId());
                       isFilmSaved(tmdbId);
                    }
                }
        );
    }

    private void switchAlert(){
        AlertDialog.Builder quit = new AlertDialog.Builder(getContext())
                .setTitle("Add To Bookmark")
                .setMessage("Do you want to add this to your 'Bookmarked' list? ")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserFilm savedFilm = new UserFilm();
                        savedFilm.setStatus("BOOKMARKED");
                        viewModel.saveUserFilm(getArguments().getLong(MOVIE_ID_KEY), savedFilm);
                        getFilm(getArguments().getLong(MOVIE_ID_KEY));
                        Toast.makeText(getContext(), "Successfully Added To Bookmarked", Toast.LENGTH_SHORT).show();
                        getUserFilm(film.getId());
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.bookmark.setChecked(false);
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
        binding.country.setText("Countries: " +  currentFilmDetails.getOrigin_country());
        binding.genres.setText(currentFilmDetails.getGenresAsString());


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

    public void backButtonLogic(){
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .popBackStackImmediate();
            }
        });
    }

    public void isFilmSaved(Long tmdbId) {
        viewModel.isUserFilmSaved(tmdbId).observe(this.getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                isSaved = aBoolean;
                if(isSaved) {
                    binding.ratingBar.setRating(userFilm.getRating());
                }
            }
        });

    }


    public String checkFilmStatus() {
        isFilmSaved(getArguments().getLong(MOVIE_ID_KEY));
        Log.i("checkFilmStatus", String.valueOf(isSaved));
        if(isSaved) {
            getFilm(getArguments().getLong(MOVIE_ID_KEY));
            viewModel.getUserFilm(film.getId());
            getUserFilm(film.getId());
            Log.i("filmDetails", userFilm.toString());
            return userFilm.getStatus();
        } else {
            return "no";
        }
    }


    public void handleFilmStatusLogic() {
        Log.i("checkFilmStatus", checkFilmStatus());
            binding.watchedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkFilmStatus().equals("WATCHED")) {
                        Toast.makeText(getContext(), "Film already watched", Toast.LENGTH_SHORT).show();
                    } else {
                        watchedAlert();
                    }
                }
            });
    }

    public void watchedAlert() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.watched_film_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = (R.style.animation);
        dialog.findViewById(R.id.save_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFilm film = new UserFilm();
                film.setStatus("WATCHED");
                film.setWatchedDate(LocalDate.now().toString());
                RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rating_in_alert);
                film.setRating(ratingBar.getNumStars());
                TextInputEditText notes = (TextInputEditText) dialog.findViewById(R.id.dialog_notes_input);
                film.setNotes(notes.getText().toString());
                if(checkFilmStatus().equals("BOOKMARKED")) {
                    viewModel.updateUserFilm(viewModel.getFilmDetailsByTmdbId(getArguments().getLong(MOVIE_ID_KEY)).getValue().getId(), film);
                    binding.bookmark.setChecked(false);
                } else {
                    viewModel.saveUserFilm(getArguments().getLong(MOVIE_ID_KEY), film);
                }
                dialog.dismiss();
                Toast.makeText(getContext(), "Successfully Added To Watched", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.findViewById(R.id.cancel_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

}