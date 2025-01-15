package com.northcoders.media_tracker_front.model;

public class AppUser {

    private Long id;

//    private Long oAuthId;
    private String username;

    public AppUser() {
    }

    public AppUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
