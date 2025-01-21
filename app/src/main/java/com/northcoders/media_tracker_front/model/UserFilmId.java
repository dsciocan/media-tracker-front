package com.northcoders.media_tracker_front.model;

public class UserFilmId {
    private Film film;
    private AppUser appUser;

    public UserFilmId() {
    }

    public UserFilmId(Film film, AppUser appUser) {
        this.film = film;
        this.appUser = appUser;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
