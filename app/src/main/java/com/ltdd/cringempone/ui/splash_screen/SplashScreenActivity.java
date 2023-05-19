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
import com.ltdd.cringempone.service.LocalStorageService;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();
        LocalStorageService.getInstance().initLocalStorage(this);
        fetchDataFromServer();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    public void fetchDataFromServer(){
        String top100s = LocalStorageService.getInstance().getString("top100s");
        if (top100s.equals("") || top100s.contains("error")){
            LocalStorageService.getInstance().putString("top100s", BaseAPIService.getInstance().getRequest("top100"));
        }
    }
}