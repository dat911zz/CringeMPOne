package com.ltdd.cringempone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.ltdd.cringempone.databinding.ActivityMainBinding;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.homebottom.HomeFragmentBottom;
import com.ltdd.cringempone.ui.person.PersonFragment;
import com.ltdd.cringempone.ui.settings.SettingsFragment;
import com.ltdd.cringempone.ui.slideshow.SlideshowFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    private ActivityMainBinding binding;

    BottomNavigationView bottomNavigationView;
    PersonFragment personFragment = new PersonFragment();
    HomeFragmentBottom homeFragmentBottom = new HomeFragmentBottom();

    SlideshowFragment slideshowFragment = new SlideshowFragment();

    SettingsFragment settingsFragment = new SettingsFragment();
    String TAG = "APP";
    String[] testRs = new String[1];
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            // Handle the exception here
            Log.e("MyApplication", "Uncaught exception occurred: " + ex.getMessage());
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        addControl();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.person:
                        fragment = new PersonFragment();
                        setTitle("Cá Nhân");
                        binding.navView.getMenu().findItem(R.id.nav_gallery).setChecked(true);
                        loadFragment(fragment);
                        return true;
                    case R.id.home:
                        fragment = new HomeFragmentBottom();
                        binding.navView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        setTitle("Trang Chủ");
                        loadFragment(fragment);
                        return true;
                }
                return true;
            }
        });
    }
    public void addControl(){
        if (!MediaControlReceiver.getInstance().isRegister){
            MediaControlReceiver.getInstance().registerReceiver(this);
        }
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_top100)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
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
            case R.id.nav_gallery:
                loadFragment(personFragment);
                bottomNavigationView.getMenu().findItem(R.id.home).setChecked(false);
                bottomNavigationView.getMenu().findItem(R.id.person).setChecked(true);
                bottomNavigationView.setSelectedItemId(R.id.person);
                return true;
            case R.id.nav_home:
                loadFragment(homeFragmentBottom);
                bottomNavigationView.getMenu().findItem(R.id.person).setChecked(false);
                bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                bottomNavigationView.setSelectedItemId(R.id.home);
                return true;
            case R.id.nav_slideshow:
                loadFragment(slideshowFragment);
                return true;
        }
        return false;
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, fragment);
        transaction.addToBackStack(null);
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        transaction.commit();
    }
}