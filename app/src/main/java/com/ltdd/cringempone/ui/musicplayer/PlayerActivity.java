package com.ltdd.cringempone.ui.musicplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.exoplayer2.Player;
import com.ltdd.cringempone.R;
import com.ltdd.cringempone.R.*;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.ltdd.cringempone.service.MediaAction;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.musicplayer.adapter.PlayerPagerAdapter;
import com.ltdd.cringempone.utils.CoreHelper;
import com.ltdd.cringempone.utils.CustomsDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.ltdd.cringempone.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "APP";
    private MediaControlReceiver mediaControl;
    private ActivityPlayerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        mediaControl = MediaControlReceiver.getInstance();
        addControl();
    }
    public void addControl(){
        Button btn = findViewById(id.btnBackToMainActivity);
        btn.setOnClickListener(v -> {
            onBackPressed();
        });
        mediaControl.addControl(this, new PlayerViewHolder(
                binding.seekBar,
                binding.play,
                binding.skipNext,
                binding.skipPrev,
                binding.shuffle,
                binding.loop,
                binding.txtStart,
                binding.txtEnd
        ));
        binding.pager.setAdapter(new PlayerPagerAdapter(getSupportFragmentManager()));
        binding.pager.setOffscreenPageLimit(3);//Prevent re-create fragment
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.playerTopTittle.setText(binding.pager.getAdapter().getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        DotsIndicator indicator = findViewById(id.pager_indicator);
        indicator.attachTo(binding.pager);
        binding.pager.setCurrentItem(1);
        sendBroadcast(new Intent(MediaAction.ACTION_PLAY));
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    private long mLastClickTime = 0;
    @Override
    public void onClick(View v) {
        controlAllBtn(false);
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        controlAllBtn(true);
    }
    private void controlAllBtn(Boolean isActive){
        binding.skipNext.setEnabled(isActive);
        binding.skipPrev.setEnabled(isActive);
        binding.play.setEnabled(isActive);
        binding.loop.setEnabled(isActive);
        binding.shuffle.setEnabled(isActive);
    }
}