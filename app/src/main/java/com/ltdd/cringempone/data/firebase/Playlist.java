package com.ltdd.cringempone.data.firebase;

import java.util.HashMap;
import java.util.Map;

public class Playlist {
    String name;
    Map<String, String> songList = new HashMap<String,String>();

    public String getName() {
        return name;
    }

    public Map<String, String> getSongList() {
        return songList;
    }

    public Playlist(String name) {
        this.name = name;
        this.songList.put("1","ABC");
        this.songList.put("2","BCD");
    }
}
