package com.northcoders.media_tracker_front.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.northcoders.media_tracker_front.MainActivity;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentSavedShowDetailsBinding;
import com.northcoders.media_tracker_front.model.UserEpisode;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.repository.UserFilmRepository;
import com.northcoders.media_tracker_front.viewmodel.SavedShowDetailsViewModel;
import com.northcoders.media_tracker_front.viewmodel.UserEpisodeViewModel;

import java.util.ArrayList;
import java.util.List;


public class SavedShowDetailsFragment extends Fragment {

    private static final String SHOW_ID = "showId";
    private FragmentSavedShowDetailsBinding binding;
    private UserFilmRepository repository;
    private UserShow show = new UserShow();
    UserShow currentShowDetails;
    SavedShowDetailsViewModel viewModel;
    UserEpisodeViewModel episodeViewModel;
    List<UserEpisode> showEpisodes = new ArrayList<>();
    List<UserEpisode> filteredList = new ArrayList<>();



    private Long mShowId;

    public SavedShowDetailsFragment() {
        // Required empty public constructor
    }

    public static SavedShowDetailsFragment newInstance(Long showId) {
        SavedShowDetailsFragment fragment = new SavedShowDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(SHOW_ID, showId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       viewModel = new ViewModelProvider(this).get(SavedShowDetailsViewModel.class);
       episodeViewModel = new ViewModelProvider(this).get(UserEpisodeViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_show_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
        if (getArguments() != null) {
            mShowId = getArguments().getLong(SHOW_ID);
            getShowDetails(mShowId);
        }

        editTextViewLogic();
        setSaveButtonLogic();
//        spinnerFunc();

        binding.autoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("textChanged", binding.autoComplete.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                String selection = binding.autoComplete.getText().toString();
                if(!s.toString().equalsIgnoreCase(show.getStatus())) {
                    moveAlert(selection);
                }
            }
        });
        getShowEpisodes();

        episodeCountIncrease();
    }


    public void getShowDetails(Long id) {
        viewModel.getSingleUserShow(id).observe(getViewLifecycleOwner(), new Observer<UserShow>() {
            @Override
            public void onChanged(UserShow userShow) {
                show = userShow;
                setBinding();
            }
        });
    }

    public void setBinding() {
        binding.setUserShow(show);
        if(!show.getStatus().equalsIgnoreCase("WATCHED")) {
            binding.currentEp.setText(String.valueOf(show.getEpisodesWatched() + 1));
        } else {
            binding.currentEp.setText(String.valueOf(show.getEpisodesWatched()));
        }
        binding.bookmarkedFragmentRatingBar.setRating(show.getRating());
        Glide.with(this).load(show.getUserShowId().getShow().getPosterUrl()).into(binding.bookmarkedFragmentImage);
        spinnerFunc();
    }

    private void moveAlert(String status) {

        AlertDialog.Builder quit = new AlertDialog.Builder(getContext())
                .setTitle(String.format("Move to %s", status))
                .setMessage(String.format("Are you sure you want to move this to your %s list?", status))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Watched Show Fragment", "USER CONFIRMED DELETE");
                        UserShow userShow = binding.getUserShow();
                        userShow.setStatus(status.toUpperCase());
                        viewModel.updateUserShow(mShowId, userShow);
                        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                        startActivity(intent);

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.autoComplete.setText(show.getStatus());
                        dialog.cancel();
                    }
                });
        quit.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getShowDetails(getArguments().getLong(SHOW_ID));
    }

    public void spinnerFunc() {

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getContext(), R.array.status_buttons, R.layout.dropdown_item);
        binding.autoComplete.setText(show.getStatus().substring(0,1) + show.getStatus().substring(1).toLowerCase());
        binding.autoComplete.setAdapter(statusAdapter);

    }

    public void editTextDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Update Show")
                .setMessage("Would you like to apply these changes?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentShowDetails = show;
                        String inputText = binding.bookmarkedFragmentNotesInput.getText().toString();
                        int rating = (int) binding.bookmarkedFragmentRatingBar.getRating();
                        currentShowDetails.setNotes(inputText);
                        currentShowDetails.setRating(rating);
                        viewModel.updateUserShow(currentShowDetails.getUserShowId().getShow().getId(),currentShowDetails);
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Show Updated", Toast.LENGTH_SHORT).show();
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


    public void getShowEpisodes() {
        episodeViewModel.getAllUserEpisodesByShow(getArguments().getLong(SHOW_ID)).observe(getViewLifecycleOwner(), new Observer<List<UserEpisode>>() {
            @Override
            public void onChanged(List<UserEpisode> userEpisodes) {
                showEpisodes = userEpisodes;
                filteredList = new ArrayList<>(showEpisodes.stream().filter(ep -> !ep.isWatched()).toList());

//                episodeCountIncrease();
            }
        });
    }

    public UserEpisode getCurrentEpisode() {
        filteredList = new ArrayList<>(showEpisodes.stream().filter(ep -> !ep.isWatched()).toList());
        if(filteredList.isEmpty()) {
            return showEpisodes.get(showEpisodes.size() - 1);
        } else {
            return filteredList.get(0);
        }
    }

public void modificationTest() {

}



    public void episodeCountIncrease() {
        binding.addEpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (filteredList.isEmpty() || filteredList.size() == 1) {
                            return;
                        }
                        UserEpisode currentEp = filteredList.get(0);
                        UserEpisode nextEp = filteredList.get(1);
                        binding.currentEp.setText(String.valueOf(nextEp.getUserEpisodeId().getEpisode().getEpisodeNumber()));
                        currentEp.setWatched(!currentEp.isWatched());
                        episodeViewModel.updateUserEpisode(currentEp.getUserEpisodeId().getEpisode().getId(), currentEp);
                        filteredList.remove(currentEp);
                        show.setEpisodesWatched(currentEp.getUserEpisodeId().getEpisode().getEpisodeNumber());
                        viewModel.updateUserShow(show.getUserShowId().getShow().getId(),show);
                    }
                });

                binding.addEpBtn.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        episodeAlert();
                        return true;
                    }
                });
    }


    public void episodeAlert() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.episode_dialogue);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = (R.style.animation);
        TextView totalEp = dialog.findViewById(R.id.total_episodes);
        totalEp.setText("/ " + show.getUserShowId().getShow().getNumberOfEpisodes());
        dialog.findViewById(R.id.save_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText epNumber = (EditText) dialog.findViewById(R.id.dialog_notes_input);
                int value = Integer.parseInt(epNumber.getText().toString());
                if(value > showEpisodes.size()) {
                    Toast.makeText(getContext(), "This show doesn't have that many episodes", Toast.LENGTH_SHORT).show();
                } else {
                    for(int i = 0; i  < value; i++) {
                        UserEpisode ep = showEpisodes.get(i);
                        ep.setWatched(true);
                        episodeViewModel.updateUserEpisode(ep.getUserEpisodeId().getEpisode().getId(), ep);
                        show.setEpisodesWatched(value);
                        viewModel.updateUserShow(show.getUserShowId().getShow().getId(), show);
                    }
                    Toast.makeText(getContext(), "Episode count updated", Toast.LENGTH_SHORT).show();
                    binding.currentEp.setText(String.valueOf(value));
                    dialog.dismiss();
                }

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