package com.ltdd.cringempone.data.dto;

import java.util.ArrayList;
import java.util.List;

public class TopDTO {
    public class Artist{
        public String id;
        public String name;
        public String link;
        public boolean spotlight;
        public String alias;
        public String thumbnail;
        public String thumbnailM;
        public boolean isOA;
        public boolean isOABrand;
        public String playlistId;
        public int totalFollow;
    }

    public class Genre{
        public String name;
    }

    public class Item{
        public String encodeId;
        public String title;
        public String thumbnail;
        public boolean isoffical;
        public String link;
        public boolean isIndie;
        public String releaseDate;
        public String sortDescription;
        public int releasedAt;
        public ArrayList<String> genreIds;
        public boolean pR;
        public ArrayList<Artist> artists;
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
    }

    public class Root{
        public String sectionType;
        public String viewType;
        public String title;
        public String link;
        public String sectionId;
        public ArrayList<Item> items;
        public Genre genre;
    }
}
