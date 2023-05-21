package com.ltdd.cringempone.data.firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String password;
    private String email;
    private String uid;

    public ArrayList<String> getFavoriteSong() {
        return favoriteSong;
    }

    ArrayList<String> favoriteSong = new ArrayList<>();
    private String EmailVerified;
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

    public String getUid() {
        return uid;
    }
    public String getEmailVerified() {
        return EmailVerified;
    }



    public Map<String, Playlist> getPlaylists() {
        return playlists;
    }

    public User() {
    }
    public User( String password, String email, String name, String uid, String EmailVerified) {
        this.EmailVerified = EmailVerified;
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.email = email;
        this.favoriteSong.add("1");
        this.playlists = null;
    }
    public User(String password, String email, String name, String uid) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.email = email;
        this.favoriteSong.add("1");
        this.playlists = null;
    }

    public User(String email, String name, String uid) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.favoriteSong.add("1");
        this.playlists = null;
    }

}
