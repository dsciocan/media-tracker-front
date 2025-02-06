package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.widget.ArrayAdapter;
import android.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
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
    private ProfileFragment profileFragment = new ProfileFragment();
    SearchResultFragment searchResultFragment = new SearchResultFragment();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ShowSearchResultViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageButton profilePicture = binding.profilepicturemain ;
        Glide.with(profilePicture)
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                .circleCrop()
//                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.circularcustombutton)
                .into(profilePicture);

        spinnerFunc();
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

        // Initialize SearchView
        SearchView searchView = view.findViewById(R.id.SearchViewHome);
        searchView.clearFocus();



        binding.buttonSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchView.getQuery().toString();
//                if(binding.searchSpinner.getSelectedItem().toString().equalsIgnoreCase("film")) {
                    SearchResultFragment fragment = SearchResultFragment.newInstance(query, binding.autoComplete.getText().toString());
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayoutFragment,fragment)
                            .addToBackStack("FilmSearchResultsTransaction")
                            .commit();
//                } else {
//                    ShowSearchFragment fragment = ShowSearchFragment.newInstance(query);
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frameLayoutFragment,fragment)
//                            .addToBackStack("ShowSearchResultTransaction")
//                            .commit();
//                }
                }
//                Bundle bundle = new Bundle();
//                bundle.putString("SearchQuery",searchView.getQuery().toString());

        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }
    @Override
    public void onItemClick(int position) {
    }

    public void spinnerFunc() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.search_options,
                R.layout.dropdown_item
        );
        binding.autoComplete.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        spinnerFunc();
    }
}