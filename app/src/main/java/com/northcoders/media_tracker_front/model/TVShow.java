package com.northcoders.media_tracker_front.model;

import java.util.List;

public class TVShow {

    //TODO: datatype for genres?

    private Long id;
    private String title;
    private String synopsis;
    private String country;
    private String language;
    private int releaseYear;
    private int finishedYear;
    private int numberOfSeasons;
    private int numberOfEpisodes;
    private boolean isComplete;
    private List<String> genres;

    //private List<Episode> episodes;

    public TVShow() {
    }

    public TVShow(Long id, String title, String synopsis, String country, String language, int releaseYear, int finishedYear, int numberOfSeasons, int numberOfEpisodes, boolean isComplete, List<String> genres) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.country = country;
        this.language = language;
        this.releaseYear = releaseYear;
        this.finishedYear = finishedYear;
        this.numberOfSeasons = numberOfSeasons;
        this.numberOfEpisodes = numberOfEpisodes;
        this.isComplete = isComplete;
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getFinishedYear() {
        return finishedYear;
    }

    public void setFinishedYear(int finishedYear) {
        this.finishedYear = finishedYear;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

}
