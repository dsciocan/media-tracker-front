package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.BookmarkedAdapter;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.databinding.FragmentBookmarkedBinding;
import com.northcoders.media_tracker_front.model.CommonViewItem;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.viewmodel.BookmarkedViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class BookmarkedFragment extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    List<CommonViewItem> itemList;
    List<UserFilm> userFilmList = new ArrayList<>();
    List<UserShow> userShowList = new ArrayList<>();
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
        getBookmarkedFilms();
        getBookmarkedShows();
        displayBookmarkedInRecyclerView();
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

    private void getBookmarkedFilms() {
        viewModel.getBookmarkedFilms().observe(getViewLifecycleOwner(), new Observer<List<UserFilm>>() {
            @Override
            public void onChanged(List<UserFilm> list) {
                userFilmList = list;
                displayBookmarkedInRecyclerView();
            }
        });
    }

    private void getBookmarkedShows() {
        viewModel.getBookmarkedShows().observe(getViewLifecycleOwner(), new Observer<List<UserShow>>() {
            @Override
            public void onChanged(List<UserShow> list) {
                userShowList = list;
                Log.i("DataCheck", userShowList.toString());
                displayBookmarkedInRecyclerView();
            }
        });
    }



    private List<CommonViewItem> convertData(List<UserFilm> userFilms, List<UserShow>userShows) {
        List<CommonViewItem> recyclerList = new ArrayList<>();
        if(!Objects.isNull(userShows) && !userShows.isEmpty()) {
            for (UserFilm userFilm : userFilms) {
                CommonViewItem commonViewItem = new CommonViewItem();
                commonViewItem.setTitle(userFilm.getUserFilmId().getFilm().getTitle());
                commonViewItem.setSynopsis(userFilm.getUserFilmId().getFilm().getSynopsis());
                commonViewItem.setPosterUrl(userFilm.getUserFilmId().getFilm().getPoster_url());
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
                commonViewItem.setType("show");
                recyclerList.add(commonViewItem);
            }
        }
        if(!recyclerList.isEmpty()) {
            recyclerList.sort(Comparator.comparing(CommonViewItem::getTitle));
        }
        return recyclerList;
    }

    private void displayBookmarkedInRecyclerView() {
//        getBookmarkedShows();
//        getBookmarkedFilms();
        itemList = convertData(userFilmList,userShowList);
        recyclerView = binding.bookmarkedRecyclerview;
        adapter = new BookmarkedAdapter(itemList, this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        if(itemList.get(position).getType().equals("film")) {
            for(UserFilm film : userFilmList) {
                if(film.getUserFilmId().getFilm().getTitle().equals(itemList.get(position).getTitle())) {
                    Long id = film.getUserFilmId().getFilm().getId();
                    BookmarkedDetailsFragment bookmarkedDetailsFragment = new BookmarkedDetailsFragment().newInstance(id);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayoutFragment,bookmarkedDetailsFragment)
                            .addToBackStack("BookmarkedTransaction")
                            .commit();
                }
            }
        } else {
            for(UserShow show : userShowList) {
                if(show.getUserShowId().getShow().getTitle().equals(itemList.get(position).getTitle())) {
                    Long id = show.getUserShowId().getShow().getId();
                    BookmarkedShowDetailsFragment bookmarkedShowDetailsFragment = BookmarkedShowDetailsFragment.newInstance(id);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayoutFragment,bookmarkedShowDetailsFragment)
                            .addToBackStack("BookmarkedTransaction")
                            .commit();
                }
            }
        }
//        Long id = userFilmList.get(position).getUserFilmId().getFilm().getId();

//        transaction.replace(R.id.frameLayoutFragment, BookmarkedDetailsFragment.class, null);
//        transaction.commit();
    }
}