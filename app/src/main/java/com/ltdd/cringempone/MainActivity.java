package com.ltdd.cringempone;

import static com.ltdd.cringempone.ui.account.AccountFragment.imageAvatar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ltdd.cringempone.api.ApiUtils;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.api.CringeAPIService;
import com.ltdd.cringempone.databinding.ActivityMainBinding;
import com.ltdd.cringempone.ui.account.AccountFragment;
import com.ltdd.cringempone.ui.activity.LoginActivity;
import com.ltdd.cringempone.ui.activity.RegisterActivity;
import com.ltdd.cringempone.ui.homebottom.HomeFragmentBottom;
import com.ltdd.cringempone.ui.person.PersonFragment;
import com.ltdd.cringempone.ui.settings.SettingsFragment;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    TextView tvName;
    TextView tvEmail;
    ImageView imgAvatar;

    public static final int REQUEST_CODE = 10;
    public static Uri uri = null;


    String TAG = "APP";

    String[] testRs = new String[1];
    CringeAPIService mService;
    NavigationView navigationView;
    DrawerLayout drawer;
    NavController navController;



    BaseAPIService apiService = BaseAPIService.getInstance();

    Boolean isBound = false;
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

//        Button btnMedia = findViewById(R.id.go_media_btn);
//        btnMedia.setOnClickListener(v -> {
//            Intent intentMd = new Intent(this, PlayerActivity.class);
//            startActivity(intentMd);
//        });

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        tvName = navigationView.getHeaderView(0).findViewById(R.id.name);
        tvEmail = navigationView.getHeaderView(0).findViewById(R.id.email);
        imgAvatar = navigationView.getHeaderView(0).findViewById(R.id.imageView);

        showUserInformation();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_top100, R.id.nav_acc)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.person);
        navClick();

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
                            startActivity(intent);
                            finish();
                            break;
                        }


                    }
                }
                drawer.closeDrawer(GravityCompat.START);
                return handled;
            }
        });
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
        getMenuInflater().inflate(R.menu.main, menu);
/*        MenuItem menuItem = menu.findItem(R.id.action_login);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);*/
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
                Intent intent1 = new Intent(this, RegisterActivity.class);
                startActivity(intent1);
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