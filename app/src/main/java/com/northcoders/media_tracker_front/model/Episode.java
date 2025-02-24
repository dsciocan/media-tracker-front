package com.northcoders.media_tracker_front.model;

public class Episode {
    private Long id;
    private Show show;
    private String title;
    private String description;
    private int seasonNumber;
    private int episodeNumber;
    private int runTime;

    public Episode() {
    }

    public Episode(Long id, Show show, String title, String description, int seasonNumber, int episodeNumber, int runTime) {
        this.id = id;
        this.show = show;
        this.title = title;
        this.description = description;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.runTime = runTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Show getTvShow() {
        return show;
    }

    public void setTvShow(Show show) {
        this.show = show;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeason(int season) {
        this.seasonNumber = season;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }
}
