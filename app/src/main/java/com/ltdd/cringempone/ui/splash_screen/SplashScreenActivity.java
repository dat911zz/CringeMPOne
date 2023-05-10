package com.ltdd.cringempone.ui.splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.ltdd.cringempone.MainActivity;
import com.ltdd.cringempone.R;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.data.dto.TopDTO;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();
        fetchDataFromServer();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 5000);
    }
    public void fetchDataFromServer(){
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("LocalStorage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                //Fetch new data from server
                String top100res = BaseAPIService.getInstance().getRequest("top100");
                editor.putString("top100s", top100res);
                editor.commit();
//                ArrayList<TopDTO> top100List = BaseAPIService.getInstance().getTop100List(top100res);
//                top100List.forEach(item -> {
//                    item.items.forEach(playlist ->{
//                        editor.putString(playlist.encodeId, BaseAPIService.getInstance().getRequest("getDetailPlaylist", playlist.encodeId));
//                    });
//                });
//                editor.commit();
                Log.d("SP", "fetchDataFromServer: run every 1 hour");
            }
        }, 0, 1, TimeUnit.HOURS);
    }
}