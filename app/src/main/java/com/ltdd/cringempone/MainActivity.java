package com.ltdd.cringempone;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.ui.activity.MusicPlayer;

import com.ltdd.cringempone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    String TAG = "APP";
    BaseAPIService apiService = BaseAPIService.getInstance();
    Boolean isBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main_link);

        bindAPIBase();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        //#region Streaming media player
//        //Set up
//        //https://mp3-s1-zmp3.zmdcdn.me/5a7ab8a550e1b9bfe0f0/2684602975505725241?authen=exp=1679135555~acl=/5a7ab8a550e1b9bfe0f0/*~hmac=4a1ff5882b7f905fb7241eaf281c725b&fs=MTY3ODk2MjmUsIC1NTmUsICzM3x3ZWJWNnwwfDU0LjI1NC4xNjIdUngMTM4
//        String url_exo = "https://www.youtube.com/watch?v=WPl10ZrhCtk";
//        PlayerView playerView = findViewById(R.id.exoPlayer);
//        ExoPlayer exoPlayer = new ExoPlayer.Builder(this).build();
//        playerView.setPlayer(exoPlayer);
//
//        // Build the media item.
//        MediaItem mediaItem = MediaItem.fromUri(url_exo);
//        // Set the media item to be played.
//        exoPlayer.setMediaItem(mediaItem);
//        // Prepare the player.
//        exoPlayer.prepare();
//        // Start the playback.
//        exoPlayer.play();
//        //#endregion
//        ImageView img = findViewById(R.id.imgView);
//        img.setImageDrawable(Helper.LoadImageFromWebOperations("https://photo-resize-zmp3.zmdcdn.me/w165_r1x1_jpeg/cover/8/5/5/b/855bb71b9bc9a577ea6627df65a2adeb.jpg"));
//
//        Button player = findViewById(R.id.btnMusicPlayer);
//        player.setOnClickListener(view -> {
////            exoPlayer.stop();
//            Intent intent = new Intent(view.getContext(), MusicPlayer.class);
//            startActivity(intent);
//        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }
    public void bindAPIBase(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(apiService.getTop100());
        String prettyJsonString = gson.toJson(je);
        Log.i(TAG, "bindAPIBase: " + prettyJsonString);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume: " + apiService.getTop100());
//        Zmp3API apiZ = new Zmp3API(this);
//        apiZ.makeAPICall();
//        RequestQueue rq = Volley.newRequestQueue(this);
//        Request request = new StringRequest(Request.Method.GET, apiSample, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i(TAG, "StringRequest onResponse: " + response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "StringRequest onErrorResponse: " + error.getMessage());
//            }
//        });
//        rq.add(request);
//
//
//        SharedPreferences sharedPref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        try {
//            String source = sharedPref.getString("returnResponse", "");
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            JsonParser jp = new JsonParser();
//            JsonElement je = jp.parse(source);
//            String prettyJsonString = gson.toJson(je);
//            Log.d(TAG, "onResume: " + prettyJsonString);
//            TextView tvMain = findViewById(R.id.txtMain);
//            tvMain.setText(prettyJsonString);
//
//        } catch (Exception e) {
//            Log.e(TAG, "onResume: " + e.getMessage());
//        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}