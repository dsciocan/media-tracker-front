package com.northcoders.media_tracker_front.model;

public class EpisodeSearchResult {
    private String air_date;
    private String name;
    private String overview;
    private int runtime;
    private int season_number;
    private int episode_number;

    public EpisodeSearchResult() {
    }

    public EpisodeSearchResult(String air_date, String name, String overview, int runtime, int season_number, int episode_number) {
        this.air_date = air_date;
        this.name = name;
        this.overview = overview;
        this.runtime = runtime;
        this.season_number = season_number;
        this.episode_number = episode_number;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }
}


