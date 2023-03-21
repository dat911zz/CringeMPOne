package com.ltdd.cringempone.data;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class BaseAPIService extends Service {
    private String hostAPI;
    public BaseAPIService() {
        hostAPI = "https://zingmp3api-dvn.onrender.com/";
    }
    private final IBinder iBinder = new LocalBinder();
    private final Random mGenerator = new Random();
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
    public int gernerateRand(){
        return mGenerator.nextInt(200);
    }
    public class LocalBinder extends Binder {
        public BaseAPIService getService (){
            return BaseAPIService.this;
        }
    }
}