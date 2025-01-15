package com.northcoders.media_tracker_front.model;

public class Episode {
    private Long id;
    private Show show;
    private String title;
    private String description;
    private int season;
    private int episodeNumber;
    private int runTime;

    public Episode() {
    }

    public Episode(Long id, Show show, String title, String description, int season, int episodeNumber, int runTime) {
        this.id = id;
        this.show = show;
        this.title = title;
        this.description = description;
        this.season = season;
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

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
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
