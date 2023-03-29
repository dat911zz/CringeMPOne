package com.ltdd.cringempone.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ltdd.cringempone.data.dto.SongInfoDTO;

import java.util.ArrayList;

public class BaseAPIService {
    private String hostAPI = "https://zingmp3api-dvn.onrender.com/";
    private String LOG_TAG = "Base API Service";
    static GsonBuilder builder;
    static Gson gson;
    public static BaseAPIService getInstance(){
        builder = new GsonBuilder();
        gson = builder.create();
        return new BaseAPIService();
    }
    public BaseAPIService() {
    }
    public String getRequest(String path){
        HttpGetRequest httpGetRequest = new HttpGetRequest();
        httpGetRequest.execute(hostAPI + path);
        try {
            Log.i("BaseAPI", httpGetRequest.get());
            return httpGetRequest.get();
        } catch (Exception e) {
            Log.e(LOG_TAG, "getRequest: " + e.getMessage());
            return String.format("{\"err\":\"{0}\",\"mess:\":\"{1}\"}","404",e.getMessage());
        }
    }
    public String getTop100(){
        return getRequest("top100");
    }
    public String getHome(){
        return getRequest("Home");
    }
    public SongInfoDTO.SongFullInfoDTO getSong(String id)
    {
        //id test: ZWABWOFZ
        String res = getRequest("getFullInfo/" + id);
        SongInfoDTO.SongFullInfoDTO songStreaming = gson.fromJson(res, SongInfoDTO.SongFullInfoDTO.class);
        return songStreaming;
    }
}