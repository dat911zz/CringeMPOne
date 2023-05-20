package com.ltdd.cringempone.data.dto;

import com.google.gson.annotations.SerializedName;

public class Streaming {
    public String err;
    public String msg;
    public String url;
    public DataStreamingDTO data;
    public class DataStreamingDTO{
        @SerializedName(value = "128")
        public String _128;
        @SerializedName(value = "320")
        public String _320;
    }
}



