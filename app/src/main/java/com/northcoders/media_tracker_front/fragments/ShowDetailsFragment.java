package com.northcoders.media_tracker_front.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentMovieBinding;
import com.northcoders.media_tracker_front.databinding.FragmentShowDetailsBinding;
import com.northcoders.media_tracker_front.model.Film;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.model.Show;
import com.northcoders.media_tracker_front.model.ShowDetails;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.UserShowId;
import com.northcoders.media_tracker_front.viewmodel.MovieDetailsViewModel;
import com.northcoders.media_tracker_front.viewmodel.ShowDetailsViewModel;

import java.time.LocalDate;
import java.util.Objects;


public class ShowDetailsFragment extends Fragment {

    private static final String SHOW_ID_KEY = "ShowKey";
    ShowDetails showDetails = new ShowDetails();
    ShowDetailsViewModel viewModel;
    private ProfileFragment profileFragment = new ProfileFragment();
    FragmentShowDetailsBinding binding;
    UserShow userShow = new UserShow();
    Show show = new Show();
    String status = "Add to List";



    public ShowDetailsFragment() {
        // Required empty public constructor
    }


    public static ShowDetailsFragment newInstance(Long showId) {
        ShowDetailsFragment fragment = new ShowDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(SHOW_ID_KEY, showId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ShowDetailsViewModel.class);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Long id = getArguments().getLong(SHOW_ID_KEY);
            getShowDetails(id);
//            isShowSaved(id);
            getShow(id);
//            getUserShow(show.getId());
        }
        backButtonLogic();
        handleShowStatusLogic();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_show_details, container, false);
        return binding.getRoot();
    }


    public void bindContent(ShowDetails showDetails){
        binding.setShow(showDetails);
        spinnerFunc();
        Glide.with(binding.poster.getContext())
                .load(showDetails.getPoster_path())
                .into(binding.poster);
        ImageButton profilePicture = binding.avatar ;
        Glide.with(profilePicture)
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                .circleCrop()
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
    }


    private void switchAlert(String status){
        AlertDialog.Builder quit = new AlertDialog.Builder(getContext())
                .setTitle(String.format("Add to %s", status))
                .setMessage(String.format("Do you want to add this to your '%s' list?", status))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserShow savedShow = new UserShow();
                        savedShow.setStatus(status.toUpperCase());
                        viewModel.saveUserShow(getArguments().getLong(SHOW_ID_KEY), savedShow);
                        Toast.makeText(getContext(), String.format( "Successfully Added To %s", status), Toast.LENGTH_SHORT).show();
                        if(status.equalsIgnoreCase("Watched")) {
                            watchedAlert();
                        }
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


    public void removeAlert() {
        AlertDialog.Builder quit = new AlertDialog.Builder(getContext())
                .setTitle("Remove From Bookmarks")
                .setMessage("Do you want to remove this from your 'Bookmarked' list? ")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO WIP DONT FORGET
                        getShow(getArguments().getLong(SHOW_ID_KEY));
                        Show savedShow = show;
//                        viewModel.deleteUserFilm(savedFilm.getId());
                        Toast.makeText(getContext(), "Successfully Removed From Bookmarked", Toast.LENGTH_SHORT).show();

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

    private void getShow(Long tmdbId) {
        viewModel.getShowDetailsByTmdbId(tmdbId).observe(getViewLifecycleOwner(), new Observer<Show>() {
                    @Override
                    public void onChanged(Show newShow) {
                        show = newShow;
                        getUserShow(show.getId());
                        Log.i("checkShowStatus", String.valueOf(userShow));
//                        isShowSaved(tmdbId);
                    }
                }
        );
    }

    private void getUserShow(Long id) {
        viewModel.getUserShow(id).observe(getViewLifecycleOwner(), new Observer<UserShow>() {
            @Override
            public void onChanged(UserShow newShow) {
                userShow = newShow;
                status = userShow.getStatus().substring(0,1) + userShow.getStatus().substring(1).toLowerCase();
                binding.autoComplete.setText(userShow.getStatus().substring(0,1) + userShow.getStatus().substring(1).toLowerCase());

            }
        });
    }




    public void getShowDetails(Long id){
        viewModel.getShowDetails(id).observe(getViewLifecycleOwner(), new Observer<ShowDetails>() {
            @Override
            public void onChanged(ShowDetails newShowDetails) {
                showDetails = (ShowDetails) newShowDetails;
                bindContent(showDetails);
            }
        });
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


    public void handleShowStatusLogic() {
        binding.autoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String selection = binding.autoComplete.getText().toString();
                if(!status.equalsIgnoreCase(binding.autoComplete.getText().toString())) {
                    switchAlert(selection);
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
                UserShow show = userShow;
                show.setStatus("WATCHED");
                RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rating_in_alert);
                show.setRating(ratingBar.getNumStars());
                TextInputEditText notes = (TextInputEditText) dialog.findViewById(R.id.dialog_notes_input);
                show.setNotes(notes.getText().toString());
                viewModel.updateUserShow(getArguments().getLong(SHOW_ID_KEY), show);

                dialog.dismiss();
                Toast.makeText(getContext(), String.format("Successfully Added To Watched"), Toast.LENGTH_SHORT).show();
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


    public void spinnerFunc() {
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getContext(), R.array.status_buttons, R.layout.dropdown_item);
        binding.autoComplete.setAdapter(statusAdapter);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getShowDetails(getArguments().getLong(SHOW_ID_KEY));
//    }
}
