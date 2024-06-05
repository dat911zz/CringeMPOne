package com.ltdd.cringempone.api;

public class ApiUtils {
    public static final String BASE_URL = "https://cringe-mp3-api.vercel.app/";
    public static CringeAPIService getCringeAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(CringeAPIService.class);
    }
}
