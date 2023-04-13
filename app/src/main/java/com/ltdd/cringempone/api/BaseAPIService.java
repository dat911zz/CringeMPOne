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
            if (httpGetRequest.get() == null){
                throw new Exception("No Respond!");
            }
            String result = httpGetRequest.get();
            Log.i("BaseAPI", result);
            return result;
        } catch (Exception e) {
            String err = e.getMessage() != null ? e.getMessage() : "";
            Log.e(LOG_TAG, "getRequest: " + err);
            return String.format("{\"err\":\"404\",\"mess:\":\"No respond\"}");
        }
    }
    public String getTop100(){
        return getRequest("top100");
    }
    public String getHome(){
        return getRequest("Home");
    }
    public SongInfoDTO getSong(String id)
    {
        //id test: ZWABWOFZ
        String res = getRequest("getFullInfo/" + id);
        if (res.contains("err")){
            return null;
        }
        SongInfoDTO songStreaming = gson.fromJson(res, SongInfoDTO.class);
        return songStreaming;
    }
}