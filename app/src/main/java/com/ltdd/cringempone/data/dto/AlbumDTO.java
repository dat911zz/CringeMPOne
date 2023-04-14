package com.ltdd.cringempone.data.dto;

import java.util.ArrayList;

public class AlbumDTO {
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
}
