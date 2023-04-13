package com.ltdd.cringempone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ltdd.cringempone.api.ApiUtils;
import com.ltdd.cringempone.api.BaseAPIService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.ltdd.cringempone.api.CringeAPIService;
import com.ltdd.cringempone.databinding.ActivityMainBinding;
import com.ltdd.cringempone.ui.musicplayer.PlayerActivity;
import com.ltdd.cringempone.ui.person.PersonFragment;
import com.ltdd.cringempone.ui.settings.SettingsFragment;
import com.ltdd.cringempone.ui.homebottom.HomeFragmentBottom;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    String TAG = "APP";
    String[] testRs = new String[1];
    CringeAPIService mService;

    BaseAPIService apiService = BaseAPIService.getInstance();
    Boolean isBound = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main_link);
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            // Handle the exception here
            Log.e("MyApplication", "Uncaught exception occurred: " + ex.getMessage());
        });
        bindAPIBase();
        mService = ApiUtils.getCringeAPIService();
        Log.d(TAG, "onCreate: hello?");


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        Button btnMedia = findViewById(R.id.go_media_btn);
        btnMedia.setOnClickListener(v -> {
            Intent intentMd = new Intent(this, PlayerActivity.class);
            startActivity(intentMd);
        });

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
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.person);

    }
    private void loadRS(){
        Call<ResponseBody> responseBodyCall = mService.getTop100();
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.i(TAG, "onResponse: " + response.body().toString());
                    testRs[0] = String.valueOf(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error loading from API");
            }
        });
        Log.i(TAG, "onCreate: " + testRs[0]);
    }
    PersonFragment personFragment = new PersonFragment();
    HomeFragmentBottom homeFragmentBottom = new HomeFragmentBottom();
    SettingsFragment settingsFragment = new SettingsFragment();
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
        Log.i(TAG, "onStart: " + testRs[0]);
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
        // Inflate the menu; this adds itemDTOS to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.person:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, personFragment).commit();
                return true;

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, homeFragmentBottom).commit();
                return true;

            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, settingsFragment).commit();
                return true;
        }
        return false;
    }
}