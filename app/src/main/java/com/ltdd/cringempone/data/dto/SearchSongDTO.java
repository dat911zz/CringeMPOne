package com.ltdd.cringempone.data.dto;

import java.util.ArrayList;

public class SearchSongDTO {
    public String encodeId;
    public String title;
    public String alias;
    public boolean isOffical;
    public String username;
    public String artistsNames;
    public ArrayList<SearchArtistDTO> artists;
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
    public String distributor;
    public ArrayList<String> indicators;
    public boolean isIndie;
    public int streamingStatus;
    public boolean allowAudioAds;
    public boolean hasLyric;
}
