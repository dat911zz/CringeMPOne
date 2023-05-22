package com.ltdd.cringempone.data.dto;

import java.util.ArrayList;

public class ItemDTO {
    public ItemDTO(String encodeId, String title, String thumbnail, boolean isoffical, String link, boolean isIndie, String releaseDate, String sortDescription, int releasedAt, ArrayList<String> genreIds, boolean pR, ArrayList<ArtistDTO> artists, String artistsNames, int playItemMode, int subType, int uid, String thumbnailM, boolean isShuffle, boolean isPrivate, String userName, boolean isAlbum, String textType, boolean isSingle, String distributor, boolean hasLyric, String mvlink) {
        this.encodeId = encodeId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.isoffical = isoffical;
        this.link = link;
        this.isIndie = isIndie;
        this.releaseDate = releaseDate;
        this.sortDescription = sortDescription;
        this.releasedAt = releasedAt;
        this.genreIds = genreIds;
        this.pR = pR;
        this.artists = artists;
        this.artistsNames = artistsNames;
        this.playItemMode = playItemMode;
        this.subType = subType;
        this.uid = uid;
        this.thumbnailM = thumbnailM;
        this.isShuffle = isShuffle;
        this.isPrivate = isPrivate;
        this.userName = userName;
        this.isAlbum = isAlbum;
        this.textType = textType;
        this.isSingle = isSingle;
        this.distributor = distributor;
        this.hasLyric = hasLyric;
        this.mvlink = mvlink;
    }

    public String encodeId;
    public String title;
    public String thumbnail;
    public boolean isoffical;
    public String link;
    public boolean isIndie;
    public String releaseDate;
    public String sortDescription;
    public long releasedAt;
    public ArrayList<String> genreIds;
    public boolean pR;
    public ArrayList<ArtistDTO> artists;
    public String artistsNames;
    public int playItemMode;
    public int subType;
    public int uid;
    public String thumbnailM;
    public boolean isShuffle;
    public boolean isPrivate;
    public String userName;
    public boolean isAlbum;
    public String textType;
    public boolean isSingle;
    public String distributor;
    public boolean hasLyric;
    public String mvlink;
    public boolean isOwner;
    public boolean canEdit;
    public boolean canDelete;
}
