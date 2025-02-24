package com.northcoders.media_tracker_front.model;

public class UserEpisode {
    private UserEpisodeId userEpisodeId;
    private int rating;
    private String notes;
    private boolean watched;
    private String watchedDate;

    public UserEpisode() {
    }

    public UserEpisode(UserEpisodeId userEpisodeId, int rating, boolean watched, String notes, String watchedDate) {
        this.userEpisodeId = userEpisodeId;
        this.rating = rating;
        this.watched = watched;
        this.notes = notes;
        this.watchedDate = watchedDate;
    }


    public UserEpisodeId getUserEpisodeId() {
        return userEpisodeId;
    }

    public void setUserEpisodeId(UserEpisodeId userEpisodeId) {
        this.userEpisodeId = userEpisodeId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(String watchedDate) {
        this.watchedDate = watchedDate;
    }
}
