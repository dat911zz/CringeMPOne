package com.ltdd.cringempone.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.ltdd.cringempone.data.dto.Streaming;
import com.ltdd.cringempone.data.dto.TopDTO;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BaseAPIService {
    private String hostAPI = "https://cringe-mp3-api.vercel.app/api/";
    private String LOG_TAG = "Base API Service";
    static GsonBuilder builder;
    static Gson gson;
    public static BaseAPIService getInstance(){
        return new BaseAPIService();
    }
    public BaseAPIService() {
        builder = new GsonBuilder();
        gson = builder.create();
    }
    public String getRequest(String path, String... pram){
        HttpGetRequest httpGetRequest = new HttpGetRequest();
        httpGetRequest.execute(hostAPI + path + "/" + (pram.length <= 0  ? "" : pram[0]));
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
    public ArrayList<TopDTO> getTop100List(String top100res) {
        if (top100res.contains("err")) {
            return null;
        }
        return new Converter<>(TopDTO.class).getList(top100res);
    }
    public SongInfoDTO getSong(String id)
    {
        //id test: ZWABWOFZ
        String res = getRequest("getFullInfo", id);
        if (res.contains("err")){
            return null;
        }
        return new Converter<SongInfoDTO>(SongInfoDTO.class).get(res);
    }
    public Streaming getStreaming(String songId){
        String res = getRequest("getStreaming", songId);
        if (res.contains("err")){
            return null;
        }
        return new Converter<Streaming>(Streaming.class).get(res);
    }
    public static class Converter<T>{
        final Class<T> cls;
        public Converter(Class<T> cls) {
            this.cls = cls;
        }
        public T get(String jsonRes){
            return gson.fromJson(jsonRes, cls);
        }
        public ArrayList<T> getList(String jsonRes){
            Type listType = TypeToken.getParameterized(ArrayList.class, cls).getType();
            return gson.fromJson(jsonRes, listType);
        }
    }
}