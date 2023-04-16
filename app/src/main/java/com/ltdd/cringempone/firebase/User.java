package com.ltdd.cringempone.firebase;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name, password, email;
    Map<String, String> favoriteSong = new HashMap<String, String>();
    Map<String, Playlist> playlists = new HashMap<String, Playlist>();


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
    public Map<String, String> getFavoriteSong() {
        return favoriteSong;
    }

    public Map<String, Playlist> getPlaylists() {
        return playlists;
    }

    public User( String password, String email) {
        this.name = email;
        this.password = password;
        this.email = email;
        this.favoriteSong = null;
        this.playlists = null;
    }
}
