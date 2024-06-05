package com.ltdd.cringempone.ui.playlist.model;

public class PlaylistItem {
    private String id, title, artistsNames,imgLink;

    public PlaylistItem(String id, String title, String artistsNames, String imgLink) {
        this.id = id;
        this.title = title;
        this.artistsNames = artistsNames;
        this.imgLink = imgLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistsNames() {
        return artistsNames;
    }

    public void setArtistsNames(String artistsNames) {
        this.artistsNames = artistsNames;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
