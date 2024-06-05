package com.ltdd.cringempone.data.dto;

import java.util.ArrayList;

public class SongInfoDTO {
    public SongInfoDTO(String encodeId, String title, String alias, boolean isOffical, String username, String artistsNames, ArrayList<ArtistDTO> artists, boolean isWorldWide, String thumbnailM, String link, String thumbnail, int duration, boolean zingChoice, boolean isPrivate, boolean preRelease, int releaseDate, ArrayList<String> genreIds, ArrayList<Object> indicators, int radioId, boolean isIndie, String mvlink, int streamingStatus, int[] downloadPrivileges, boolean allowAudioAds, boolean hasLyric, int userid, ArrayList<GenreDTO> genres, ArrayList<ComposerDTO> composers, String distributor, AlbumDTO album, RadioDTO radio, boolean isRBT, int like, int listen, boolean liked, int comment, Streaming streaming) {
        this.encodeId = encodeId;
        this.title = title;
        this.alias = alias;
        this.isOffical = isOffical;
        this.username = username;
        this.artistsNames = artistsNames;
        this.artists = artists;
        this.isWorldWide = isWorldWide;
        this.thumbnailM = thumbnailM;
        this.link = link;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.zingChoice = zingChoice;
        this.isPrivate = isPrivate;
        this.preRelease = preRelease;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.indicators = indicators;
        this.radioId = radioId;
        this.isIndie = isIndie;
        this.mvlink = mvlink;
        this.streamingStatus = streamingStatus;
        this.downloadPrivileges = downloadPrivileges;
        this.allowAudioAds = allowAudioAds;
        this.hasLyric = hasLyric;
        this.userid = userid;
        this.genres = genres;
        this.composers = composers;
        this.distributor = distributor;
        this.album = album;
        this.radio = radio;
        this.isRBT = isRBT;
        this.like = like;
        this.listen = listen;
        this.liked = liked;
        this.comment = comment;
        this.streaming = streaming;
    }

    public String encodeId;
    public String title;
    public String alias;
    public boolean isOffical;
    public String username;
    public String artistsNames;
    public ArrayList<ArtistDTO> artists;
    public boolean isWorldWide;
    public String thumbnailM;
    public String link;
    public String thumbnail;
    public int duration;
    public boolean zingChoice;
    public boolean isPrivate;
    public boolean preRelease;
    public int releaseDate;
    public ArrayList<String> genreIds;
    public ArrayList<Object> indicators;
    public int radioId;
    public boolean isIndie;
    public String mvlink;
    public int streamingStatus;
    public int[] downloadPrivileges;
    public boolean allowAudioAds;
    public boolean hasLyric;
    public int userid;
    public ArrayList<GenreDTO> genres;
    public ArrayList<ComposerDTO> composers;
    public String distributor;
    public AlbumDTO album;
    public RadioDTO radio;
    public boolean isRBT;
    public int like;
    public int listen;
    public boolean liked;
    public int comment;
    public Streaming streaming;

}
