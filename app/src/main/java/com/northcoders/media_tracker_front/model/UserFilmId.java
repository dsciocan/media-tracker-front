package com.northcoders.media_tracker_front.model;

public class UserFilmId {
    private Film film;

    public UserFilmId() {
    }
    public UserFilmId(Film film) {
        this.film = film;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
