package com.ltdd.cringempone;

import static com.ltdd.cringempone.ui.account.AccountFragment.imageAvatar;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ltdd.cringempone.databinding.ActivityMainBinding;
import com.ltdd.cringempone.service.LocalStorageService;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.activity.LoginActivity;
import com.ltdd.cringempone.ui.activity.RegisterActivity;
import com.ltdd.cringempone.ui.homebottom.HomeFragmentBottom;
import com.ltdd.cringempone.ui.person.PersonFragment;
import com.ltdd.cringempone.ui.search.SearchResult;
import com.ltdd.cringempone.ui.settings.SettingsFragment;
import com.ltdd.cringempone.utils.CoreHelper;
import com.ltdd.cringempone.ui.slideshow.SlideshowFragment;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    PersonFragment personFragment = new PersonFragment();
    HomeFragmentBottom homeFragmentBottom = new HomeFragmentBottom();
    SlideshowFragment slideshowFragment = new SlideshowFragment();
    TextView tvName;
    TextView tvEmail;
    ImageView imgAvatar;

    public static final int REQUEST_CODE = 10;
    public static Uri uri = null;


    String TAG = "APP";
    String[] testRs = new String[1];
    NavigationView navigationView;
    DrawerLayout drawer;
    NavController navController;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if(intent == null){
                            return;}
                        uri = intent.getData();

                        try {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageAvatar.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }});


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
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_top100)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.person);

        tvName = navigationView.getHeaderView(0).findViewById(R.id.name);
        tvEmail = navigationView.getHeaderView(0).findViewById(R.id.email);
        imgAvatar = navigationView.getHeaderView(0).findViewById(R.id.imageView);

        showUserInformation();
        navClick();


        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                LocalStorageService.getInstance().putString("isConnect", Boolean.toString(CoreHelper.isConnected(getBaseContext())));
            }
        }, 0, 5000);
    }

    private void navClick()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
                if (!handled) {
                    switch (item.getItemId()) {
                        case R.id.nav_logout: {
                            LoginManager.getInstance().logOut();//Đăng xuất khỏi facebook
                            FirebaseAuth.getInstance().signOut();//Đăng xuất khỏi Firebase Auth
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                            break;
                        }
                    }
                }
                drawer.closeDrawer(GravityCompat.START);
                return handled;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaControlReceiver.getInstance().unregisterReceiver(this);
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
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.action_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.action_settings:
                Toast.makeText(getBaseContext(),"Settings",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,SearchResult.class));
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_register).setVisible(false);

        }
        else{
            menu = navigationView.getMenu();
            MenuItem nav_logout = menu.findItem(R.id.nav_logout);
            nav_logout.setVisible(false);
            MenuItem nav_acc = menu.findItem(R.id.nav_acc);
            nav_acc.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    public void showUserInformation()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null)
        {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        if(name == null)
        {
            tvName.setVisibility(View.GONE);
        }
        else {
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(name);

        }
        tvEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.avatar_default).into(imgAvatar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openGallery();
            }
        }
    }

    public void openGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }
}