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
    public void initLocalStorage(Context context){
        if (this.context == null){
            this.context = context;
        }
        prefs = context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE);
    }
    public String getString(String key){
        return prefs.getString(key, "");
    }
    public void putString(String key, String value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void clear(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
