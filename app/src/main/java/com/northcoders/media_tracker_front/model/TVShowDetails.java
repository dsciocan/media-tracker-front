package com.northcoders.media_tracker_front.model;

import java.util.List;

public class TVShowDetails {

    //TODO: datatype for genres?

    private Long id;
    private String name;
    private String firstAirDate;
    private String lastAirDate;
    private String originalLanguage;
    private String overview;
    private boolean inProduction;
    private int numberOfEpisodes;
    private int numberOfSeasons;
    private List<String> createdBy;
    private List<Integer> episodeRunTime;
    private List<String> country;

    //  private List<Genre> genres,

    public TVShowDetails() {
    }

    public TVShowDetails(Long id, String name, String firstAirDate, String lastAirDate, String originalLanguage, String overview, boolean inProduction, int numberOfEpisodes, int numberOfSeasons, List<String> createdBy, List<Integer> episodeRunTime, List<String> country) {
        this.id = id;
        this.name = name;
        this.firstAirDate = firstAirDate;
        this.lastAirDate = lastAirDate;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
        this.inProduction = inProduction;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.createdBy = createdBy;
        this.episodeRunTime = episodeRunTime;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<String> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<String> createdBy) {
        this.createdBy = createdBy;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }
}
