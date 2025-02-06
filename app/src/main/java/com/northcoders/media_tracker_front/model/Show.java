package com.northcoders.media_tracker_front.model;

import java.util.List;

public class Show {

    //TODO: datatype for genres?

    private Long id;
    private Long tmdbId;
    private String title;
    private String synopsis;
    private String country;
    private String language;
    private int releaseYear;
    private int finishedYear;
    private int numberOfSeasons;
    private int numberOfEpisodes;
    private boolean isComplete;
    private String posterUrl;
    private List<String> genres;


    //private List<Episode> episodes;

    public Show() {
    }

    public Show(Long id, Long tmdbId, String title, String synopsis, String country, String language, int releaseYear, int finishedYear, int numberOfSeasons, int numberOfEpisodes, boolean isComplete, String posterUrl, List<String> genres) {
        this.id = id;
        this.tmdbId = tmdbId;
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
        this.posterUrl = posterUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
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

    public String getSeasonsAndEpisodes() {
        return numberOfSeasons + " seasons | " + numberOfEpisodes + " eps";
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String yearAndStatus() {
        StringBuilder builder = new StringBuilder(releaseYear);
        builder.append("-");
        builder.append(finishedYear);
        builder.append(" | ");
        if(!isComplete) {
            builder.append("Ongoing");
        } else {
            builder.append("Finished");
        }
        return builder.toString();
    }

    public String getGenresAsString() {
        StringBuilder genreStringBuilder = new StringBuilder();
        for (String genre : genres) {
            if(!genre.equals(genres.get(0))) {
                genreStringBuilder.append(", ");
            }
            genreStringBuilder.append(genre);
        }
        return genreStringBuilder.toString();
    }

}
