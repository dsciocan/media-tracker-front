package com.northcoders.media_tracker_front.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentWatchedMovieBinding;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.viewmodel.WatchHistoryViewModel;

public class WatchedMovieFragment extends Fragment {

    UserFilm currentFilmDetails;

    UserFilm currentFilm = new UserFilm();

    WatchHistoryViewModel viewModel;
    FragmentWatchedMovieBinding binding;
    WatchedFragment watchedFragment = new WatchedFragment();

    private final static String MOVIE_ID_KEY = "moviekey";


    public WatchedMovieFragment() {
        // Required empty public constructor
    }


    public static WatchedMovieFragment newInstance(Long movieId) {
        WatchedMovieFragment fragment = new WatchedMovieFragment();
        Bundle args = new Bundle();
        args.putLong(MOVIE_ID_KEY, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WatchHistoryViewModel.class);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
        if (getArguments() != null) {
            getUserFilmDetails(getArguments().getLong(MOVIE_ID_KEY));
        }
        loadProfileButton();
        backButtonLogic();
        setDeleteButtonLogic();
        editTextViewLogic();
        setSaveButtonLogic();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_watched_movie, container, false);
        return binding.getRoot();
    }

    public void getUserFilmDetails(Long id) {
        viewModel.getWatchedFilmDetails(id).observe(getViewLifecycleOwner(), new Observer<UserFilm>() {
            @Override
            public void onChanged(UserFilm watchHistory) {
                currentFilm = watchHistory;
                displayInPage(currentFilm);
            }
        });
    }


    public void setDeleteButtonLogic() {
        binding.watchedDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert();
            }
        });
    }

    private void deleteAlert() {
        AlertDialog.Builder quit = new AlertDialog.Builder(getContext())
                .setTitle("Remove From Watched")
                .setMessage("Are you sure you want to remove this from your 'watched' list?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Watched Movie Fragment", "USER CONFIRMED DELETE");
                        // Backend only has call to delete user film -> not necessarily by status
                        // Same logic applied for both watched and bookmarked
                        viewModel.deleteUserFilm(currentFilm.getUserFilmId().getFilm().getId());
                        Toast.makeText(getContext(), "Successfully Removed", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayoutFragment, new WatchedFragment())
                                .commit();
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

    public void backButtonLogic(){
        binding.movieFragmentBackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayoutFragment, watchedFragment)
                        .commit();
            }
        });
    }

    private void editTextViewLogic() {
        binding.movieFragmentNotesInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    editTextDialog();
                    return true;
                }
                return false;
            }
        });
    }

    public void setSaveButtonLogic() {
        binding.watchedSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  editTextDialog();
            }
        });
    }

    public void editTextDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Update Movie")
                .setMessage("Would you like to apply these changes?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentFilmDetails = currentFilm;
                        String inputText = binding.movieFragmentNotesInput.getText().toString();
                        int rating = (int) binding.movieFragmentRatingBar.getRating();
                        currentFilmDetails.setNotes(inputText);
                        currentFilmDetails.setRating(rating);
                        viewModel.updateUserFilm(currentFilmDetails.getUserFilmId().getFilm().getId(),currentFilmDetails);
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Movie updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }



    private void loadProfileButton() {
        ImageButton profilePicture = binding.profilepicturewatched;
        Glide.with(profilePicture)
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                .circleCrop()
//                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.circularcustombutton)
                .into(profilePicture);


        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
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

    }


    public void displayInPage(UserFilm watchHistory) {
        binding.movieFragmentTitle.setText(watchHistory.getUserFilmId().getFilm().getTitle());
        binding.movieYear.setText("" + watchHistory.getUserFilmId().getFilm().getReleaseYear());
        binding.movieRuntime.setText(watchHistory.getUserFilmId().getFilm().getDuration() + " mins");
        binding.movieLanguage.setText(watchHistory.getUserFilmId().getFilm().getLanguage());
        binding.movieCountry.setText(watchHistory.getUserFilmId().getFilm().getCountry());

        Glide.with(binding.movieFragmentImage.getContext())
                .load(watchHistory.getUserFilmId().getFilm().getPoster_url())
                .into(binding.movieFragmentImage);

        binding.movieGenres.setText(watchHistory.getUserFilmId().getFilm().getGenresAsString());
        TextView detailsTextView = binding.movieFragmentDetailsCard.findViewById(R.id.details_text_view);
        detailsTextView.setText(watchHistory.getUserFilmId().getFilm().getSynopsis());
        binding.movieFragmentNotesInput.setText(watchHistory.getNotes());
        int rating = watchHistory.getRating();
        binding.movieFragmentRatingBar.setRating(rating);
        binding.detailsTextView.setMovementMethod(new ScrollingMovementMethod());

    }


}