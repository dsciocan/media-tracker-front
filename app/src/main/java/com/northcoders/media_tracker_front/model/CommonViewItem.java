package com.northcoders.media_tracker_front.model;

public class CommonViewItem {

    String title;
    String synopsis;
    String posterUrl;
    String type;
    String watchedDate;

    public CommonViewItem() {
    }

    public CommonViewItem(String title, String synopsis, String posterUrl, String type, String watchedDate) {
        this.title = title;
        this.synopsis = synopsis;
        this.posterUrl = posterUrl;
        this.type = type;
        this.watchedDate = watchedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(String watchedDate) {
        this.watchedDate = watchedDate;
    }
}
