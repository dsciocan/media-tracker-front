package com.northcoders.media_tracker_front.model;

public class UserShowId {
    private Show show;
    private AppUser appUser;

    public UserShowId() {
    }

    public UserShowId(Show show, AppUser appUser) {
        this.show = show;
        this.appUser = appUser;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
