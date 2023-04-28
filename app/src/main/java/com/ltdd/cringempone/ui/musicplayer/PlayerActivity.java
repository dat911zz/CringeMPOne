package com.ltdd.cringempone.ui.musicplayer;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ltdd.cringempone.R.*;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.ltdd.cringempone.service.MediaControlReceiver;

public class PlayerActivity extends AppCompatActivity {
    private String TAG = "APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_player);
        getSupportActionBar().hide();
        MediaControlReceiver mediaControl = MediaControlReceiver.getInstance();
        mediaControl.registerReceiver(this);
        SongInfoDTO song = BaseAPIService.getInstance().getSong("ZWABWOFZ");
        if (song != null){
            mediaControl.setCurrentSong(song);

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
            viewPager.setCurrentItem(1);
        }
        else{
            Toast.makeText(this,"Có lỗi đã xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
        }

//        Helper.FragmentUtil.addFragment(this, MainPlayerFragment.newInstance(player.isPlaying(), "Một Bước Lên Mây", "Tester số 1 VN"), R.id.media_fragment_holder, null);
    }


}