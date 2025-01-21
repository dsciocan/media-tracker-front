package com.northcoders.media_tracker_front.model;

public class FilmSearchResult {
    private Long id;
    private String overview;
    private String poster_path;
    private String title;
    //year only?
    private String release_date;

    public FilmSearchResult() {
    }

    public FilmSearchResult(Long id, String overview, String poster_path, String title, String release_date) {
        this.id = id;
        this.overview = overview;
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
