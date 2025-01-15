package com.northcoders.media_tracker_front.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/*
    The data from the fetch request is going to be stored in this class.
    Need empty constructor and one with all the fields.
    Each field needs a getter (add @Bindable) and setter.
 */

public class WatchHistory extends BaseObservable {
    String title;
    String info;
    int rating;
    public WatchHistory(){}
    public WatchHistory(String title, String info, int rating) {
        this.title = title;
        this.info = info;
        this.rating = rating;
    }

    @Bindable
    public String getRating() {
        return Integer.toString(rating);
    }

    // make sure the argument is a numeric string
    public void setRating(String rating) {
        this.rating = Integer.parseInt(rating);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
