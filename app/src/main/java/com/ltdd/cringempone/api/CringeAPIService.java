package com.ltdd.cringempone.api;

import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.ltdd.cringempone.data.dto.TopDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CringeAPIService {
    @GET("/getFullInfo")
    Call<ResponseBody> getSongInfo(@Query("id") String id);
    @GET("/getTop100")
    Call<ResponseBody> getTop100();
}
