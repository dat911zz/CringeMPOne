package com.ltdd.cringempone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.api.CringeAPIService;
import com.ltdd.cringempone.databinding.ActivityMainBinding;
import com.ltdd.cringempone.ui.homebottom.HomeFragmentBottom;
import com.ltdd.cringempone.ui.person.PersonFragment;
import com.ltdd.cringempone.ui.settings.SettingsFragment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        fetchDataFromServer();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_top100)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.person);
    }
    PersonFragment personFragment = new PersonFragment();
    HomeFragmentBottom homeFragmentBottom = new HomeFragmentBottom();
    SettingsFragment settingsFragment = new SettingsFragment();
    public void fetchDataFromServer(){
        //Run job every 30 mins
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("LocalStorage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                //Fetch new data from server
                editor.putString("top100s", BaseAPIService.getInstance().getTop100Json());
                editor.commit();
                Log.d(TAG, "fetchDataFromServer: run every 30 mins");
            }
        }, 0, 30, TimeUnit.MINUTES);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: " + testRs[0]);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_login);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.action_register:
                Toast.makeText(getBaseContext(),"Đăng ký",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings:
                Toast.makeText(getBaseContext(),"Settings",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
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
        }
        return false;
    }
}