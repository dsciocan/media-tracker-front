package com.northcoders.media_tracker_front.model;

import java.util.List;

public class ShowDetails {

    //TODO: datatype for genres?

    private Long id;
    private String name;
    private String first_air_date;
    private String last_air_date;
    private String original_language;
    private String overview;
    private boolean in_production;
    private int number_of_episodes;
    private int number_of_seasons;
    private List<String> created_by;
    private List<Integer> episode_run_time;
    private List<String> origin_country;

    //  private List<Genre> genres,

    public ShowDetails() {
    }

    public ShowDetails(Long id, String name, String first_air_date, String last_air_date, String original_language, String overview, boolean in_production, int number_of_episodes, int number_of_seasons, List<String> created_by, List<Integer> episode_run_time, List<String> origin_country) {
        this.id = id;
        this.name = name;
        this.first_air_date = first_air_date;
        this.last_air_date = last_air_date;
        this.original_language = original_language;
        this.overview = overview;
        this.in_production = in_production;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
        this.created_by = created_by;
        this.episode_run_time = episode_run_time;
        this.origin_country = origin_country;
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

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public List<String> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(List<String> created_by) {
        this.created_by = created_by;
    }

    public List<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(List<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(List<String> origin_country) {
        this.origin_country = origin_country;
    }
}
