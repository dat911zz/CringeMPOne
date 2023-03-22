package com.ltdd.cringempone.api;

import android.util.Log;

public class BaseAPIService {
    private String hostAPI = "https://zingmp3api-dvn.onrender.com/";
    private String LOG_TAG = "Base API Service";
    public static BaseAPIService getInstance(){
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
}