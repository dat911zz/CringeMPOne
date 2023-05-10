package com.ltdd.cringempone.ui.musicplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.exoplayer2.Player;
import com.ltdd.cringempone.R.*;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.ltdd.cringempone.service.MediaAction;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.musicplayer.adapter.PlayerPagerAdapter;
import com.ltdd.cringempone.utils.Helper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.ltdd.cringempone.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {
    private String TAG = "APP";
    private MediaControlReceiver mediaControl;
    ActivityPlayerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_player);
        getSupportActionBar().hide();
        mediaControl = MediaControlReceiver.getInstance();
        if (!mediaControl.isRegister){
            mediaControl.registerReceiver(this);
        }
        addControl();
    }
    public void addControl(){
        Button btn = findViewById(id.btnBackToMainActivity);
        btn.setOnClickListener(v -> {
            onBackPressed();
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        this.runOnUiThread(() -> {
            mediaControl.addControl(this, new PlayerViewHolder(
                    findViewById(id.seekBar),
                    findViewById(id.play),
                    findViewById(id.skipNext),
                    findViewById(id.skipPrev),
                    findViewById(id.shuffle),
                    findViewById(id.loop),
                    findViewById(id.txtStart),
                    findViewById(id.txtEnd)
            ));
            ViewPager viewPager = findViewById(id.pager);
            viewPager.setAdapter(new PlayerPagerAdapter(getSupportFragmentManager()));
            DotsIndicator indicator = findViewById(id.pager_indicator);
            indicator.attachTo(viewPager);
            viewPager.setCurrentItem(1);
            Intent playIntent = new Intent(MediaAction.ACTION_PLAY);
            sendBroadcast(playIntent);
        });
    }
}