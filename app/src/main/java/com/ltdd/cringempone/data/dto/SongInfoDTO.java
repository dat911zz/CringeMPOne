package com.ltdd.cringempone.data.dto;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class SongInfoDTO {
    public class Album{
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
        public ArrayList<Artist> artists;
        public String artistsNames;
    }

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

    public class Composer{
        public String id;
        public String name;
        public String link;
        public boolean spotlight;
        public String alias;
        public String playlistId;
        public String cover;
        public String thumbnail;
        public int totalFollow;
    }

    public class Genre{
        public String id;
        public String name;
        public String title;
        public String alias;
        public String link;
    }

    public class Radio{
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

    public class SongFullInfoDTO{
        public String encodeId;
        public String title;
        public String alias;
        public boolean isOffical;
        public String username;
        public String artistsNames;
        public ArrayList<Artist> artists;
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
        public boolean allowAudioAds;
        public boolean hasLyric;
        public int userid;
        public ArrayList<Genre> genres;
        public ArrayList<Composer> composers;
        public String distributor;
        public Album album;
        public Radio radio;
        public boolean isRBT;
        public int like;
        public int listen;
        public boolean liked;
        public int comment;
        public Streaming streaming;
    }

    public class Streaming{
        @SerializedName(value="128")
        public String _128;
        @SerializedName(value="320")
        public String _320;
    }
}
