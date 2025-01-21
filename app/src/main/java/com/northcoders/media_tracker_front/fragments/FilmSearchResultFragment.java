package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.FilmSearchResultAdapter;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.databinding.FragmentFilmSearchResultsBinding;
import com.northcoders.media_tracker_front.model.FilmSearchResult;
import com.northcoders.media_tracker_front.viewmodel.FilmSearchResultViewModel;

import java.util.ArrayList;
import java.util.List;

public class FilmSearchResultFragment extends Fragment implements RecyclerViewInterface {
    private FragmentFilmSearchResultsBinding binding;
    private RecyclerView recyclerView;
    ArrayList<FilmSearchResult> searchResults = new ArrayList<>();
    FilmSearchResultAdapter filmSearchResultAdapter;
    FilmSearchResultViewModel viewModel;
    private static final String previousQuery = "SearchQuery";
    ProfileFragment profileFragment = new ProfileFragment();

    public FilmSearchResultFragment() {
        // Required empty public constructor
    }

    public static FilmSearchResultFragment newInstance(String query) {
        FilmSearchResultFragment fragment = new FilmSearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(previousQuery, query); // Pass the new query each time
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(FilmSearchResultViewModel.class);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        loadProfilePicture();

        if(getArguments() != null){
            getFilmResults(getArguments().getString(previousQuery));
            binding.SearchViewSearch.setQuery(getArguments().getString(previousQuery),false);
        }

//        }

        binding.SearchViewSearch.clearFocus();

        binding.SearchViewSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getFilmResults(query);
                binding.SearchViewSearch.setQuery(query,false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_film_search_results,container,false);
        // Inflate the layout for this fragment

           return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {
        long selectedFilmId = searchResults.get(position).getId();
        MovieFragment movieFragment = new MovieFragment();/*.newInstance(selectedFilmId);*/
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutFragment,movieFragment)
                .addToBackStack("MovieDetailsTransaction")
                .commit();
    }

    private void getFilmResults(String query){
        viewModel.getFilmSearchResults(query).observe(getViewLifecycleOwner(), new Observer<List<FilmSearchResult>>() {
            @Override
            public void onChanged(List<FilmSearchResult> filmSearchResults) {
                searchResults = (ArrayList<FilmSearchResult>) filmSearchResults;
                displayInRecyclerView();
            }
        });
    }


    private void loadProfilePicture(){
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
                        .addToBackStack("profileFragmentTransaction") // allow user to press back to go back to home fragment
                        .commit();
            }
        });
    }
    private void displayInRecyclerView(){
        recyclerView = binding.recyclerview;
        filmSearchResultAdapter = new FilmSearchResultAdapter(searchResults,this.getContext(),this);
        recyclerView.setAdapter(filmSearchResultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        filmSearchResultAdapter.notifyDataSetChanged();
    }


}