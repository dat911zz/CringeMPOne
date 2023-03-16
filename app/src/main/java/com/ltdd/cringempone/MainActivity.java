package com.ltdd.cringempone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ltdd.cringempone.ui.MusicPlayer;
import com.ltdd.cringempone.utils.Helper;
import com.ltdd.cringempone.utils.Zmp3API;

public class MainActivity extends AppCompatActivity {
    String TAG = "APP";
    String apiSample = "https://zingmp3api-dvn.onrender.com/top100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);


        ImageView img = findViewById(R.id.imgView);
        Log.d(TAG, "onCreate img: " + Helper.LoadImageFromWebOperations("https://photo-resize-zmp3.zmdcdn.me/w165_r1x1_jpeg/cover/8/5/5/b/855bb71b9bc9a577ea6627df65a2adeb.jpg"));
        img.setImageDrawable(Helper.LoadImageFromWebOperations("https://photo-resize-zmp3.zmdcdn.me/w165_r1x1_jpeg/cover/8/5/5/b/855bb71b9bc9a577ea6627df65a2adeb.jpg"));

        Button player = findViewById(R.id.btnMusicPlayer);
        player.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MusicPlayer.class);
            startActivity(intent);
        });

//        String url = request.getRequestLine().getUri();
//        HttpResponse realResponse = download(url);
//
//        InputStream data = realResponse.getEntity().getContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Zmp3API apiZ = new Zmp3API(this);
        apiZ.makeAPICall();
        RequestQueue rq = Volley.newRequestQueue(this);
        Request request = new StringRequest(Request.Method.GET, apiSample, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "StringRequest onResponse: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "StringRequest onErrorResponse: " + error.getMessage());
            }
        });
        rq.add(request);


        SharedPreferences sharedPref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        try {
            String source = sharedPref.getString("returnResponse", "");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(source);
            String prettyJsonString = gson.toJson(je);
            Log.d(TAG, "onResume: " + prettyJsonString);
            TextView tvMain = findViewById(R.id.txtMain);
            tvMain.setText(prettyJsonString);

        } catch (Exception e) {
            Log.e(TAG, "onResume: " + e.getMessage());
        }



    }
}