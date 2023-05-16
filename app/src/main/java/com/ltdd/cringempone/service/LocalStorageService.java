package com.ltdd.cringempone.service;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorageService {
    private static LocalStorageService instance;
    private Context context;
    private SharedPreferences prefs;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private LocalStorageService(){}
    public static LocalStorageService getInstance(){
        if (instance == null){
            instance = new LocalStorageService();
        }
        return instance;
    }
//    public void initLocalStorage(Context context){
//        if (this.context == null){
//            this.context = context;
//        }
//        getSharedPreferences("LocalStorage", Context.MODE_PRIVATE);
//    }
}
