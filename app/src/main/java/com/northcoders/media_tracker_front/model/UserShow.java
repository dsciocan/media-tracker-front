package com.northcoders.media_tracker_front.model;

public class UserShow {

    private UserShowId userShowId;
    private int rating;
    private String notes;
    private String status;
    private String watchedDate;

    public UserShow(UserShowId userFilmId, int rating, String notes, String status, String watchedDate) {
        this.userShowId = userFilmId;
        this.rating = rating;
        this.notes = notes;
        this.status = status;
        this.watchedDate = watchedDate;
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

    public String getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(String watchedDate) {
        this.watchedDate = watchedDate;
    }
}
