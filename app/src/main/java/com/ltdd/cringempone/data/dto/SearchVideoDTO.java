package com.ltdd.cringempone.data.dto;

import java.util.ArrayList;

public class SearchVideoDTO {
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
    public int streamingStatus;
    public int downloadPrivileges;
    public SearchArtistDTO artist;
}
