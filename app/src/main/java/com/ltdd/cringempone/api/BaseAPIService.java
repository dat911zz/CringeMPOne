package com.ltdd.cringempone.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ltdd.cringempone.data.dto.GenreDTO;
import com.ltdd.cringempone.data.dto.LyricDTO;
import com.ltdd.cringempone.data.dto.ResponseDTO;
import com.ltdd.cringempone.data.dto.SearchDTO;
import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.ltdd.cringempone.data.dto.Streaming;
import com.ltdd.cringempone.data.dto.TopDTO;
import com.ltdd.cringempone.service.LocalStorageService;
import com.ltdd.cringempone.service.MediaControlReceiver;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BaseAPIService {
    private String hostAPI = "https://cringe-mp3-api.vercel.app/api/";
    private String LOG_TAG = "Base API Service";
    static GsonBuilder builder;
    static Gson gson;
    public static BaseAPIService getInstance(){
        CookieHandler.setDefault(new CookieManager());
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
            return String.format("{\"error\":\"404\",\"mess:\":\"No respond\"}");
        }
    }
    public String getRequestFromURL(String url) {
        HttpGetRequest httpGetRequest = new HttpGetRequest();
        httpGetRequest.execute(url);
        try {
            if (httpGetRequest.get() == null) {
                throw new Exception("No Respond!");
            }
            String result = httpGetRequest.get();
            Log.i("BaseAPI", result);
            return result;
        } catch (Exception e) {
            String err = e.getMessage() != null ? e.getMessage() : "";
            Log.e(LOG_TAG, "getRequest: " + err);
            return String.format("{\"error\":\"404\",\"mess:\":\"No respond\"}");
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
        Streaming streaming = new Converter<>(Streaming.class).get(res);
        if (streaming.url != null){
            streaming = new Converter<Streaming>(Streaming.class).get(getRequestFromURL(streaming.url));
            streaming = new Converter<Streaming>(Streaming.class).get(getRequestFromURL(streaming.url));
        }
        return streaming;
    }
    public List<String> fetchLyricData(){
        String res = LocalStorageService.getInstance().getString("lrc_" + MediaControlReceiver.getInstance().getCurrentSong().encodeId);
        if (res.contains("error") || res.equals("")){
            res = BaseAPIService.getInstance().getRequest(
                    "getLyric",
                    MediaControlReceiver.getInstance().getCurrentSong().encodeId
            );
            LocalStorageService.getInstance().putString("lrc_" + MediaControlReceiver.getInstance().getCurrentSong().encodeId, res);
        }
        return convertRes2Sentences(res);
    }
    public List<String> convertRes2Sentences(String res){
        List<String> sentences = new ArrayList<>();

        new BaseAPIService.Converter<>(LyricDTO.class).get(res).sentences.forEach(sentence -> {
            String result = "";
            for(LyricDTO.Word word : sentence.words){
                result += " " + word.data;
            }
            sentences.add(result);
        });

        return sentences;
    }
    public boolean isDown(String url){
        try{
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int code = conn.getResponseCode();
            if (code == 404 || code == 403 || code == 500){
                return true;
            }
            return false;
        } catch (MalformedURLException e) {
            return true;
        } catch (IOException e) {
            return true;
        }
    }

    public SearchDTO getSearchResult(String txtSearch)
    {
        String res = getRequest("search", txtSearch);
        if (res.contains("err")){
            return null;
        }
        return new Converter<SearchDTO>(SearchDTO.class).get(res);
    }
    public static class Converter<T>{
        final Class<T> cls;
        public Converter(Class<T> cls) {
            this.cls = cls;
        }
        public T get(String jsonRes){
            try{
                return gson.fromJson(jsonRes, cls);
            }catch (Exception ex){
                Log.e("API Converter", "get: " + ex.getMessage());
                return null;
            }
        }
        public ArrayList<T> getList(String jsonRes){
            try{
                return gson.fromJson(jsonRes, TypeToken.getParameterized(ArrayList.class, cls).getType());
            }catch (Exception ex){
                Log.e("API Converter", "getList: " + ex.getMessage());
                return null;
            }

        }
    }
}