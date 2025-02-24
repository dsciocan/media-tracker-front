package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.material.card.MaterialCardView;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.EpisodeListAdapter;
import com.northcoders.media_tracker_front.databinding.EpisodeItemBinding;
import com.northcoders.media_tracker_front.databinding.FragmentEpisodeListBinding;
import com.northcoders.media_tracker_front.model.Show;
import com.northcoders.media_tracker_front.model.UserEpisode;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.viewmodel.BookmarkedViewModel;
import com.northcoders.media_tracker_front.viewmodel.UserEpisodeViewModel;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class EpisodeListFragment extends Fragment {

    private static final String SHOW_ID = "showId";
    FragmentEpisodeListBinding binding;
    ExpandableListView expandableListView;
    EpisodeListAdapter episodeListAdapter;
    List<String> expandableTitleList = new ArrayList<>();
    Map<String, List<UserEpisode>> expandableDetailList = new HashMap<>();
    List<UserEpisode> allEpisodes = new ArrayList<>();
    UserEpisodeViewModel viewModel;
    Show show = new Show();



    private Long mShowId;


    public EpisodeListFragment() {
        // Required empty public constructor
    }

    public static EpisodeListFragment newInstance(Long showId) {
        EpisodeListFragment fragment = new EpisodeListFragment();
        Bundle args = new Bundle();
        args.putLong(SHOW_ID, showId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserEpisodeViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_episode_list, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShowId = getArguments().getLong("showId");
        }
        binding.progressBar.setVisibility(View.VISIBLE);
        getEpisodeList();
        Log.i("LISTS", expandableTitleList.toString());
        Log.i("LISTS", allEpisodes.toString());

    }

    public void getEpisodeList() {
        viewModel.getAllUserEpisodesByShow(getArguments().getLong(SHOW_ID)).observe(getViewLifecycleOwner(), new Observer<List<UserEpisode>>() {
            @Override
            public void onChanged(List<UserEpisode> userEpisodes) {
                allEpisodes = userEpisodes;

                getShow();
            }
        });
    }

    public void  getShow() {
        viewModel.getShow(getArguments().getLong(SHOW_ID)).observe(getViewLifecycleOwner(), new Observer<Show>() {
            @Override
            public void onChanged(Show savedShow) {
                show = savedShow;
                setCategoryListEntries();
                displayInExpandedListView();
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void setCategoryListEntries() {
        int seasons = show.getNumberOfSeasons();
        for(int i = seasons; i >= 1; i--) {
            List<UserEpisode> episodesForSeason = new ArrayList<>();
            for(UserEpisode ep : allEpisodes) {
                if(ep.getUserEpisodeId().getEpisode().getSeasonNumber() == i) {
                    episodesForSeason.add(ep);
                }
                episodesForSeason.sort(new Comparator<UserEpisode>() {
                    @Override
                    public int compare(UserEpisode o1, UserEpisode o2) {
                        return o1.getUserEpisodeId().getEpisode().getEpisodeNumber() - o2.getUserEpisodeId().getEpisode().getEpisodeNumber();
                    }
                });
                expandableDetailList.put("Season " + i, episodesForSeason);
            }
        }
    }

    public void displayInExpandedListView() {
        expandableTitleList = expandableDetailList.keySet().stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(!o1.replaceAll("\\D", "").isEmpty() && !o2.replaceAll("\\D", "").isEmpty()) {
                    return Integer.parseInt(o1.replaceAll("\\D", "")) - Integer.parseInt(o2.replaceAll("\\D", ""));
                } else return 0;
            }
        }).toList();
        expandableListView = binding.expandableView;
        episodeListAdapter = new EpisodeListAdapter(getContext(), expandableTitleList, expandableDetailList);
        expandableListView.setAdapter(episodeListAdapter);

    }



}

