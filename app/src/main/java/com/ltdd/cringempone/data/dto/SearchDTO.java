package com.ltdd.cringempone.data.dto;

import java.util.ArrayList;

public class SearchDTO {
    public SearchTopDTO top;
    public ArrayList<ArtistDTO> artists;
    public ArrayList<SearchSongDTO> songs;
    public ArrayList<SearchVideoDTO> videos;
    public ArrayList<ItemDTO> playlists;
    public SearchCounterDTO counter;
    public String sectionId;
}
