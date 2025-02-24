package com.northcoders.media_tracker_front.model;

public class UserShow {

    private UserShowId userShowId;
    private int rating;
    private String notes;
    private String status;
    private Integer episodesWatched;
    private String dateStarted;
    private String dateCompleted;

    public UserShow(UserShowId userFilmId, int rating, String notes, String status, Integer episodesWatched, String dateStarted, String dateCompleted) {
        this.userShowId = userFilmId;
        this.rating = rating;
        this.notes = notes;
        this.status = status;
        this.episodesWatched = episodesWatched;
        this.dateStarted = dateStarted;
        this.dateCompleted = dateCompleted;
    }

    public UserShow() {
    }

    public UserShowId getUserShowId() {
        return userShowId;
    }

    public void setUserShowId(UserShowId userShowId) {
        this.userShowId = userShowId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEpisodesWatched() {
        return episodesWatched;
    }

    public void setEpisodesWatched(Integer episodesWatched) {
        this.episodesWatched = episodesWatched;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
}
