package com.northcoders.media_tracker_front.model;

import java.util.List;

public class FilmDetails {

    //TODO: datatype for genres?

    private String title;
    private String overview;
    private String originalLanguage;
    private String posterPath;
    //year only?
    private String releaseDate;
    private int runtime;
    private List<String> productionCompanies;

    //  List<Genre> genres

    public FilmDetails() {
    }

    public FilmDetails(String title, String overview, String originalLanguage, String posterPath, String releaseDate, int runtime, List<String> production_companies) {
        this.title = title;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.productionCompanies = production_companies;
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

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<String> getProduction_companies() {
        return productionCompanies;
    }

    public void setProduction_companies(List<String> production_companies) {
        this.productionCompanies = production_companies;
    }


}
