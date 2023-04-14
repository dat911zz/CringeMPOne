package com.ltdd.cringempone.api;

public class ApiUtils {
    public static final String BASE_URL = "https://zingmp3api-dvn.onrender.com";
    public static CringeAPIService getCringeAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(CringeAPIService.class);
    }
}
