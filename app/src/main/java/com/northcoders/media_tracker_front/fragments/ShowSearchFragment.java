package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.adapter.ShowSearchResultAdapter;
import com.northcoders.media_tracker_front.databinding.FragmentShowSearchBinding;
import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.viewmodel.ShowSearchResultViewModel;
import java.util.ArrayList;
import java.util.List;

public class ShowSearchFragment extends Fragment implements RecyclerViewInterface {
    private FragmentShowSearchBinding binding;
    RecyclerView recyclerView;
    ArrayList<ShowSearchResult> showSearchResultArrayList;
    ShowSearchResultAdapter showSearchResultAdapter;
    ShowSearchResultViewModel viewModel;

    public ShowSearchFragment() {
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
        SearchView searchView = view.findViewById(R.id.SearchViewSearch);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getShowSearchResult(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_search, container, false);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_search, container, false);
        return binding.getRoot();  // needed to see the recyclerview
    }

    private  void getShowSearchResult(String query) {
        viewModel.getShowSearchResult(query).observe(this, new Observer<List<ShowSearchResult>>() {
            @Override
            public void onChanged(List<ShowSearchResult> showSearchResultList) {
                showSearchResultArrayList = (ArrayList<ShowSearchResult>) showSearchResultList;
                displayShowSearchResultInRecyclerView();
            }
        });
    }

    private void displayShowSearchResultInRecyclerView() {
        recyclerView = binding.recyclerview;
        showSearchResultAdapter = new ShowSearchResultAdapter(showSearchResultArrayList, this.getContext(), this);
        recyclerView.setAdapter(showSearchResultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        showSearchResultAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
    }
}