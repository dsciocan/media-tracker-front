package com.northcoders.media_tracker_front.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.EpisodeItemBinding;
import com.northcoders.media_tracker_front.model.UserEpisode;
import com.northcoders.media_tracker_front.model.repository.UserEpisodeRepository;
import com.northcoders.media_tracker_front.viewmodel.UserEpisodeViewModel;
import java.util.List;
import java.util.Map;

public class EpisodeListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableTitleList;
    private Map<String, List<UserEpisode>> expandableDetailList;
    private EpisodeItemBinding binding;


    public EpisodeListAdapter(Context context, List<String> expandableTitleList, Map<String, List<UserEpisode>> expandableDetailList) {
        this.context = context;
        this.expandableTitleList = expandableTitleList;
        this.expandableDetailList = expandableDetailList;
    }

    @Override
    public int getGroupCount() {
        return expandableTitleList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return expandableDetailList.get(expandableTitleList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return expandableTitleList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return expandableDetailList.get(expandableTitleList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String seasonNumber = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setText(seasonNumber);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        UserEpisode userEpisode = (UserEpisode) getChild(groupPosition, childPosition);
        if(convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.episode_item, parent, false);
            convertView = binding.getRoot();
        } else {
          binding = (EpisodeItemBinding) convertView.getTag();
        }
        if(userEpisode.isWatched()) {
            binding.epWatchedButton.setChecked(true);
        }
        binding.setEpisode(userEpisode);

        convertView.setTag(binding);

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
