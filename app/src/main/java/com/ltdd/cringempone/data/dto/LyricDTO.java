package com.ltdd.cringempone.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LyricDTO {
    public ArrayList<Sentence> sentences;
    public String file;
    public boolean enabledVideoBG;
    public String streamingUrl;
    public ArrayList<String> defaultIBGUrls;
    @SerializedName(value = "BGMode")
    public int bGMode;

    public class Sentence{
        public ArrayList<Word> words;
    }

    public class Word{
        public int startTime;
        public int endTime;
        public String data;
    }


}
