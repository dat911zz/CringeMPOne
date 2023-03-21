package com.ltdd.cringempone.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
public class Zmp3API {
    static String URL_API = "https://zingmp3api-dvn.onrender.com/top100";
    Context context;
    RequestQueue requestQueue;
    public Zmp3API(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }
    public void makeAPICall(){
        String MyPREFERENCES = "MyPrefs";
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();

        Request request = new StringRequest(Request.Method.GET, URL_API, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                editor.putString("returnResponse", response);
                editor.apply();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                editor.putString("returnResponse", error.getMessage());
                editor.apply();
            }
        });
        requestQueue.add(request);
    }
}
