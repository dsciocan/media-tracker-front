package com.northcoders.media_tracker_front.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.LoginActivity;
import com.northcoders.media_tracker_front.R;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.media_tracker_front.adapter.BookmarkedAdapter;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.databinding.FragmentWatchedBinding;
import com.northcoders.media_tracker_front.model.CommonViewItem;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.WatchHistory;
import com.northcoders.media_tracker_front.viewmodel.WatchHistoryViewModel;
import com.northcoders.media_tracker_front.adapter.WatchHistoryAdapter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class WatchedFragment extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    List<CommonViewItem> watchHistoryArrayList;
    List<UserFilm> userFilms = new ArrayList<>();
    List<UserShow> userShows = new ArrayList<>();
    WatchHistoryAdapter watchHistoryAdapter;
    FragmentWatchedBinding binding;
    WatchHistoryViewModel viewModel;

    ProfileFragment profileFragment = new ProfileFragment();

    public WatchedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WatchHistoryViewModel.class);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWatchedFilms();
        getWatchedShows();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_watched, container, false);
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





    private void getWatchedFilms() {
        viewModel.getWatchHistory().observe(getViewLifecycleOwner(), new Observer<List<UserFilm>>() {
            @Override
            public void onChanged(List<UserFilm> list) {
                userFilms = list;
                displayWatchedInRecyclerView();
            }
        });
    }

    private void getWatchedShows() {
        viewModel.getWatchedShows().observe(getViewLifecycleOwner(), new Observer<List<UserShow>>() {
            @Override
            public void onChanged(List<UserShow> list) {
                userShows = list;
                displayWatchedInRecyclerView();
            }
        });
    }



    private List<CommonViewItem> convertData(List<UserFilm> userFilms, List<UserShow>userShows) {
        List<CommonViewItem> recyclerList = new ArrayList<>();
        if(!Objects.isNull(userFilms) && !userFilms.isEmpty()) {
            for (UserFilm userFilm : userFilms) {
                CommonViewItem commonViewItem = new CommonViewItem();
                commonViewItem.setTitle(userFilm.getUserFilmId().getFilm().getTitle());
                commonViewItem.setSynopsis(userFilm.getUserFilmId().getFilm().getSynopsis());
                commonViewItem.setPosterUrl(userFilm.getUserFilmId().getFilm().getPoster_url());
                commonViewItem.setWatchedDate(userFilm.getWatchedDate());
                commonViewItem.setType("film");
                recyclerList.add(commonViewItem);
            }
        }
        if(!Objects.isNull(userShows) && !userShows.isEmpty()) {
            for (UserShow userShow : userShows) {
                CommonViewItem commonViewItem = new CommonViewItem();
                commonViewItem.setTitle(userShow.getUserShowId().getShow().getTitle());
                commonViewItem.setSynopsis(userShow.getUserShowId().getShow().getSynopsis());
                commonViewItem.setPosterUrl(userShow.getUserShowId().getShow().getPosterUrl());
                commonViewItem.setWatchedDate(userShow.getWatchedDate());
                commonViewItem.setType("show");
                recyclerList.add(commonViewItem);
            }
        }
        if(!recyclerList.isEmpty()) {
//            recyclerList.sort(Comparator.comparing(CommonViewItem::getWatchedDate));
        }
        return recyclerList;
    }

    private void displayWatchedInRecyclerView() {
//        getBookmarkedShows();
//        getBookmarkedFilms();
        watchHistoryArrayList = convertData(userFilms,userShows);
        recyclerView = binding.recyclerview;
        watchHistoryAdapter = new WatchHistoryAdapter(watchHistoryArrayList, this.getContext(), this);
        recyclerView.setAdapter(watchHistoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        watchHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        if(watchHistoryArrayList.get(position).getType().equals("film")) {
            for(UserFilm film : userFilms) {
                if(film.getUserFilmId().getFilm().getTitle().equals(watchHistoryArrayList.get(position).getTitle())) {
                    Long id = film.getUserFilmId().getFilm().getId();
                    BookmarkedDetailsFragment bookmarkedDetailsFragment = new BookmarkedDetailsFragment().newInstance(id);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayoutFragment,bookmarkedDetailsFragment)
                            .addToBackStack("BookmarkedTransaction")
                            .commit();
                }
            }
        } else {
            for(UserShow show : userShows) {
                if(show.getUserShowId().getShow().getTitle().equals(watchHistoryArrayList.get(position).getTitle())) {
                    Long id = show.getUserShowId().getShow().getId();
                    BookmarkedDetailsFragment bookmarkedDetailsFragment = new BookmarkedDetailsFragment().newInstance(id);

                    Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
                    intent.putExtra("SHOW_ID", show.getUserShowId().getShow().getId());

                    startActivity(intent);
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frameLayoutFragment,bookmarkedDetailsFragment)
//                            .addToBackStack("BookmarkedTransaction")
//                            .commit();
                }
            }
        }
        Long id = userFilms.get(position).getUserFilmId().getFilm().getId();

//        transaction.replace(R.id.frameLayoutFragment, BookmarkedDetailsFragment.class, null);
//        transaction.commit();
    }
}