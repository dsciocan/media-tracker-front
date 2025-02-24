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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.FilmSearchResultAdapter;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.adapter.ShowSearchResultAdapter;
import com.northcoders.media_tracker_front.databinding.FragmentSearchResultsBinding;
import com.northcoders.media_tracker_front.model.FilmSearchResult;
import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.viewmodel.FilmSearchResultViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment implements RecyclerViewInterface {
    private FragmentSearchResultsBinding binding;
    private RecyclerView recyclerView;
    ArrayList<FilmSearchResult> filmSearchResultList = new ArrayList<>();
    ArrayList<ShowSearchResult> showSearchResultList = new ArrayList<>();
    FilmSearchResultAdapter filmSearchResultAdapter;
    ShowSearchResultAdapter showSearchResultAdapter;
    FilmSearchResultViewModel viewModel;
    private static final String previousQuery = "SearchQuery";
    private static final String searchType = "SearchType";
    ProfileFragment profileFragment = new ProfileFragment();

    public SearchResultFragment() {
        // Required empty public constructor
    }

    public static SearchResultFragment newInstance(String query, String type) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(previousQuery, query); // Pass the new query each time
        bundle.putString(searchType, type);
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
        if(getArguments() != null){
            String type = getArguments().getString("SearchType");
            String query = getArguments().getString("SearchQuery");
            Log.i("searchType", type);
            if(type.equalsIgnoreCase("film")) {
                getFilmResults(query);
            } else {
                getShowResults(query);
            }
            binding.SearchViewSearch.setQuery(query,false);
        }

        loadProfilePicture();
        spinnerFunc();
        binding.SearchViewSearch.clearFocus();

        binding.SearchViewSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(binding.autoComplete.getText().toString().equalsIgnoreCase("film")) {
                    getFilmResults(query);
                } else {
                    getShowResults(query);
                }
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_results,container,false);
        // Inflate the layout for this fragment
           return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {
        if(binding.autoComplete.getText().toString().equalsIgnoreCase("film")) {
            long selectedFilmId = filmSearchResultList.get(position).getId();
            MovieFragment movieFragment = new MovieFragment().newInstance(selectedFilmId);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayoutFragment, movieFragment)
                    .addToBackStack("MovieDetailsTransaction")
                    .commit();
        } else {
            long selectedShowId = showSearchResultList.get(position).getId();
            ShowDetailsFragment showDetailsFragment = new ShowDetailsFragment().newInstance(selectedShowId);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayoutFragment, showDetailsFragment)
                    .addToBackStack("ShowDetailsTransaction")
                    .commit();
        }
    }

    private void getFilmResults(String query){
        viewModel.getFilmSearchResults(query).observe(getViewLifecycleOwner(), new Observer<List<FilmSearchResult>>() {
            @Override
            public void onChanged(List<FilmSearchResult> filmSearchResults) {
                filmSearchResultList = (ArrayList<FilmSearchResult>) filmSearchResults;
                displayInRecyclerView();
            }
        });
    }

    private void getShowResults (String query) {
        viewModel.getShowSearchResults(query).observe(getViewLifecycleOwner(), new Observer<List<ShowSearchResult>>() {
            @Override
            public void onChanged(List<ShowSearchResult> showSearchResults) {
             showSearchResultList = (ArrayList<ShowSearchResult>) showSearchResults;
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
        filmSearchResultAdapter = new FilmSearchResultAdapter(filmSearchResultList,this.getContext(),this);
        showSearchResultAdapter = new ShowSearchResultAdapter(showSearchResultList, this.getContext(), this);
        if(binding.autoComplete.getText().toString().equalsIgnoreCase("tv show")) {
            recyclerView.setAdapter(showSearchResultAdapter);
        } else {
            recyclerView.setAdapter(filmSearchResultAdapter);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        filmSearchResultAdapter.notifyDataSetChanged();
    }

    public void spinnerFunc() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.search_options,
                R.layout.dropdown_item
        );
        binding.autoComplete.setText(getArguments().getString(searchType));
        binding.autoComplete.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        spinnerFunc();
    }


}