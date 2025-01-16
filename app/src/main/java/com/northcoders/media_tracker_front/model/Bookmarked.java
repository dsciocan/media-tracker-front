package com.northcoders.media_tracker_front.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Bookmarked extends BaseObservable  {

    private String title;
    private String info;
    private String rating;

    public Bookmarked() {
    }

    public Bookmarked(String title, String info, String rating) {
        this.title = title;
        this.info = info;
        this.rating = rating;
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
    @Bindable

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
