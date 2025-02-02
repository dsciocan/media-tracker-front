package com.northcoders.media_tracker_front.model;

import java.util.List;

public class FilmDetails {

    //TODO: datatype for genres?
    private List<Genre> genres;
    private String title;
    private String overview;
    private String original_language;
    private List<String> origin_country;
    private String poster_path;
    //year only?
    private String release_date;
    private int runtime;
    //private List<String> production_companies;


    public FilmDetails() {

    }

    public FilmDetails(List<Genre> genres,String title, String overview, String original_language, List<String> origin_country, String poster_path, String release_date, int runtime){// List<String> production_companies) {
        this.genres = genres;
        this.title = title;
        this.overview = overview;
        this.original_language = original_language;
        this.origin_country = origin_country;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.runtime = runtime;
   //     this.production_companies = production_companies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRuntime() {
        return String.valueOf(runtime);
    }

    public void setRuntime(String runtime) {
        this.runtime = Integer.parseInt(runtime);
    }


    public List<Genre> getGenres() {
        return genres;
    }

    public String getGenresAsString() {
        StringBuilder genreStringBuilder = new StringBuilder();
        for (Genre genre : genres) {
            if(!genre.equals(genres.get(0))) {
                genreStringBuilder.append(", ");
            }
            genreStringBuilder.append(genre.getName());
        }
        return genreStringBuilder.toString();
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(List<String> origin_country) {
        this.origin_country = origin_country;
    }


    /*public List<String> getProduction_companies() {
        return production_companies;
    }*/

//    public void setProduction_companies(List<String> production_companies) {
  //      this.production_companies = production_companies;
//    }
}
