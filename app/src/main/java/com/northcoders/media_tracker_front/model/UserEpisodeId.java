package com.northcoders.media_tracker_front.model;

public class UserEpisodeId {

    private Episode episode;
    private AppUser appUser;

    public UserEpisodeId() {
    }

    public UserEpisodeId(Episode episode, AppUser appUser) {
        this.episode = episode;
        this.appUser = appUser;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
