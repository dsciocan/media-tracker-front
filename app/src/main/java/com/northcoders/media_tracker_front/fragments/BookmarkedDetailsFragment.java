package com.northcoders.media_tracker_front.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentBookmarkedDetailsBinding;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.viewmodel.BookmarkedDetailsViewModel;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;


public class BookmarkedDetailsFragment extends Fragment {
    BookmarkedDetailsViewModel bookmarkedDetailsViewModel;
   UserFilm userFilmFilm = new UserFilm();
   UserFilm currentFilmDetails;
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
        loadProfilePicture();
        backButtonLogic();
        setDeleteButtonLogic();
        editTextViewLogic();
        watchedButton();
        setSaveButtonLogic();

    }

    public void getFilmDetails(Long id) {
        bookmarkedDetailsViewModel.getUserFilm(id).observe(getViewLifecycleOwner(), new Observer<UserFilm>() {
            @Override
            public void onChanged(UserFilm userFilm) {
                userFilmFilm = userFilm;
                setBindingText(userFilmFilm);
            }
        });
    }

    public void setBindingText(UserFilm userFilm) {
        Log.i("BOOKMARKEDDETAILSFRAGMENT", userFilm.getUserFilmId().getFilm().getTitle());
        binding.setUserFilm(userFilm);
        binding.bookmarkedFragmentRatingBar.setRating(userFilm.getRating());
//        binding.bookmarkedFragmentTitle.setText(bookmarked.getUserFilmId().getFilm().getTitle());
        Glide.with(this).load(userFilm.getUserFilmId().getFilm().getPoster_url()).into(binding.bookmarkedFragmentImage);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarked_details, container, false);
        // Inflate the layout for this fragment
    return binding.getRoot();

    }


    public void setDeleteButtonLogic() {
        binding.bookmarkedFragmentBookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.bookmarkedFragmentBookmarkBtn.isChecked()) {
                    deleteAlert();
                }
            }
        });
    }

    private void deleteAlert() {
        AlertDialog.Builder quit = new AlertDialog.Builder(getContext())
                .setTitle("Remove From Bookmarks")
                .setMessage("Are you sure you want to remove this from your 'bookmarked' list?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Watched Movie Fragment", "USER CONFIRMED DELETE");
                        // Backend only has call to delete user film -> not necessarily by status
                        // Same logic applied for both watched and bookmarked
                        bookmarkedDetailsViewModel.deleteUserFilm(userFilmFilm.getUserFilmId().getFilm().getId());
                        Toast.makeText(getContext(), "Successfully Removed", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayoutFragment, bookmarkedFragment)
                                .commit();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.bookmarkedFragmentBookmarkBtn.setChecked(true);
                        dialog.cancel();
                    }
                });
        quit.show();
    }



    public void setSaveButtonLogic() {
        binding.bookmarkedSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDialog();
            }
        });
    }
    private void editTextViewLogic(){
        binding.bookmarkedFragmentNotesInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    public void editTextDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Update Movie")
                .setMessage("Would you like to apply these changes?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentFilmDetails = userFilmFilm;
                        String inputText = binding.bookmarkedFragmentNotesInput.getText().toString();
                        int rating = (int) binding.bookmarkedFragmentRatingBar.getRating();
                        currentFilmDetails.setNotes(inputText);
                        currentFilmDetails.setRating(rating);
                        bookmarkedDetailsViewModel.updateUserFilm(currentFilmDetails.getUserFilmId().getFilm().getId(),currentFilmDetails);
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Movie Updated", Toast.LENGTH_SHORT).show();
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

    public void loadProfilePicture(){
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

    public void backButtonLogic(){
        binding.bookmarkedFragmentBackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayoutFragment, bookmarkedFragment)
                        .commit();
            }
        });
    }

    public void watchedButton() {
        binding.bookmarkedToWatchedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            moveAlert();
            }
        });
    }


    private void moveAlert() {
        AlertDialog.Builder quit = new AlertDialog.Builder(getContext())
                .setTitle("Move to Watched")
                .setMessage("Are you sure you want to move this to your 'watched' list?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Watched Movie Fragment", "USER CONFIRMED DELETE");
                        // Backend only has call to delete user film -> not necessarily by status
                        // Same logic applied for both watched and bookmarked
                        UserFilm userFilm = binding.getUserFilm();
                        userFilm.setStatus("WATCHED");
                        userFilm.setWatchedDate(LocalDate.now().toString());
                        bookmarkedDetailsViewModel.updateUserFilm(userFilm.getUserFilmId().getFilm().getId(), userFilm);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayoutFragment, bookmarkedFragment)
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

}