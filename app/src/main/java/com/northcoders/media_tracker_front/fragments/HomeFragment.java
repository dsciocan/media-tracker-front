package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.widget.Button;
import android.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.adapter.ShowSearchResultAdapter;
import com.northcoders.media_tracker_front.databinding.FragmentHomeBinding;
import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.viewmodel.ShowSearchResultViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements RecyclerViewInterface  {
    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    ArrayList<ShowSearchResult> showSearchResultArrayList;
    ShowSearchResultAdapter showSearchResultAdapter;
    ShowSearchResultViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ShowSearchResultViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Initialize SearchView
        SearchView searchView = view.findViewById(R.id.SearchViewHome);
        searchView.clearFocus();

        // Find the button by its ID
        Button mButton = view.findViewById(R.id.button_searchShow);
        // Set a click listener on the button
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                Toast.makeText(getContext(), "Button clicked!", Toast.LENGTH_SHORT).show();
                Fragment searchFragment = new ShowSearchFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutFragment, searchFragment) // Replace container with the new fragment
                        .addToBackStack(null) // Add transaction to backstack to allow navigation back
                        .commit();
            }
        });


        /*
        // implement click event
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Operations Performed On Clicking the Button
                // Are Done Here
                Log.i("hello", "button was clicked");

            }
            });
        /*
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //getShowSearchResult(query);
                Log.i("GET request", query);
                Log.i("GET request", "I am here");
                getShowSearchResult(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("GET request", newText);
                return false;
            }
        });
         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return binding.getRoot();  // needed to see the recyclerview
        //return view;                 // needed for search field
    }

    /*
    private  void getShowSearchResult(String query) {
        viewModel.getShowSearchResult(query).observe(this, new Observer<List<ShowSearchResult>>() {
            @Override
            public void onChanged(List<ShowSearchResult> showSearchResultList) {
                showSearchResultArrayList = (ArrayList<ShowSearchResult>) showSearchResultList;
                displayShowSearchResultInRecyclerView();
            }
        });
    }
    */

    /*
    private void displayShowSearchResultInRecyclerView() {
        recyclerView = binding.recyclerview;
        showSearchResultAdapter = new ShowSearchResultAdapter(showSearchResultArrayList, this.getContext(), this);
        recyclerView.setAdapter(showSearchResultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        showSearchResultAdapter.notifyDataSetChanged();
    }
*/
    @Override
    public void onItemClick(int position) {
    }
}