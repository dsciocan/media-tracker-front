package com.northcoders.media_tracker_front.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/*
    The data from the fetch request is going to be stored in this class.
    Need empty constructor and one with all the fields.
    Each field needs a getter (add @Bindable) and setter.
 */

public class Bookmarked extends BaseObservable  {

    private String title;
    private String posterUrl;

    public Bookmarked() {
    }

    public Bookmarked(String title, String posterUrl) {
        this.title = title;
        this.posterUrl = posterUrl;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
