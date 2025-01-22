package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentStatsBinding;
import com.northcoders.media_tracker_front.viewmodel.ShowSearchResultViewModel;
import com.northcoders.media_tracker_front.viewmodel.StatsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * todo:
 *  - Find a way to bind data to the charts once the data is available
 *  - Decide what charts are used + What stats are shown
 *  - Place charts for today,weekly,monthly,yearly stats
 *  - Replace dummy information with information from backend
 */

/**
 * In order to add multiple charts in a single layout:
 * <a href="https://github.com/AnyChart/AnyChart-Android/wiki/Multiple-charts-in-a-single-layout">Guide</a>
 */


public class StatsFragment extends Fragment {

    FragmentStatsBinding binding;
    StatsViewModel viewModel;
    Map<String,Integer> map ;
    List<DataEntry> dataEntriesGenres = new ArrayList<>();
    List<DataEntry> dataRuntime = new ArrayList<>();
    Pie pie;
    Cartesian cartesian;
    Column column;
    Bar bar;
    ProfileFragment profileFragment = new ProfileFragment();


    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StatsViewModel.class);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setSubTitle();
        loadProfilePicture();
        // Set Progress bar
        binding.StatsChartView.setProgressBar(binding.StatsProgressBar);
        binding.StatsChartViewTwo.setProgressBar(binding.StatsProgressBarTwo);
        setBarChart();
        getBarData();
        setPieChart();
        getGenreStats();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_stats,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void setBarChart(){
        // Set active AnyChart view for this chart
        APIlib.getInstance().setActiveAnyChartView(binding.StatsChartView);

        cartesian = AnyChart.bar();

        cartesian.animation(true);
        cartesian.title("Total Watchtime for This week");

        cartesian.yAxis(0).title("Minutes");
        cartesian.background().enabled(true);
        cartesian.background().fill("#F0F8FF");

        cartesian.labels().fontColor("#696969");
        cartesian.title().fontColor("#696969");

        binding.StatsChartView.setChart(cartesian);
    }



    private void getBarData(){

        viewModel.getTotalRuntimeStat().observe(getViewLifecycleOwner(),number ->{
            if(number != null){
                Log.i("Stats Repository", String.valueOf(number) + " WOOOOOOO");
                APIlib.getInstance().setActiveAnyChartView(binding.StatsChartView);
                dataRuntime.clear();
                dataRuntime.add(new ValueDataEntry("",number));

                    cartesian.bar(dataRuntime);


            }else{
                Log.e("Stats Fragent", "NOOOOOOOOOOOOO DATA");
            }
        });

    }


    private void setSubTitle(){
        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String subTitle = "Hi " + name + ",\nHere are your Stats!";
        binding.StatsSubtitle.setText(subTitle);

    }
    private void setPieChart(){

        APIlib.getInstance().setActiveAnyChartView(binding.StatsChartViewTwo);
        pie = AnyChart.pie();


        // Set pie chart titles and legend (?) and positions
        pie.title("Genres watched this week");
        pie.labels().position("outside");
        pie.legend().title().enabled(true);
        pie.legend().title().text("Genre").padding(0d,0d,10d,0d);
        pie.legend().position("left").itemsLayout(LegendLayout.VERTICAL).align(Align.LEFT);
        // Set the pie chart to the Chart view via the binding

        //Add custom theme - appears to choose by position (colors taken from default anychart palette "pastel")
        //See list of allowed values: https://docs.anychart.com/Appearance_Settings/Colors_Table

        String[] pastels = new String[] {"#80cbc4", "#e6ee9c", "#ffcc80", "#ffab91", "#f8bbd0", "#d1c4e9", "#9e9e9e", "#c7b299", "#90caf9" , "#aed581"};
        pie.palette(pastels);
        pie.labels().fontColor("#696969");
        pie.title().fontColor("#696969");
        pie.legend().fontColor("#696969");
        pie.background().enabled(true);
        pie.background().fill("#F0F8FF");

        binding.StatsChartViewTwo.setChart(pie);

    }

    private void getGenreStats(){
        viewModel.getGenreStats().observe(getViewLifecycleOwner(), stringIntegerMap -> {
            if(stringIntegerMap != null && !stringIntegerMap.isEmpty()){
                dataEntriesGenres.clear();

                for(Map.Entry<String,Integer> entry : stringIntegerMap.entrySet()){
                    dataEntriesGenres.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
                }
                APIlib.getInstance().setActiveAnyChartView(binding.StatsChartViewTwo);
                pie.data(dataEntriesGenres);
                Log.i("StatsFragment", "Pie chart updated with new data");
            }
            else {
                Log.e("StatsFragment", "No genre stats available");
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



}