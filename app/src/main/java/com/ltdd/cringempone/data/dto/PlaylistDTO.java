package com.ltdd.cringempone.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaylistDTO {
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
    @SerializedName(value = "PR")
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
    public String description;
    public String aliasTitle;
    public String sectionId;
    public int contentLastUpdate;
    public ArtistDTO artist;
    public ArrayList<GenreDTO> genres;
    public SongDTO song;
    public int like;
    public int listen;
    public boolean liked;
    public boolean isOwner;
    public boolean canEdit;
    public boolean canDelete;
}
