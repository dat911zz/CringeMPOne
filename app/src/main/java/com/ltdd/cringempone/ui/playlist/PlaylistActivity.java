package com.ltdd.cringempone.ui.playlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.PlaylistDTO;
import com.ltdd.cringempone.ui.playlist.adapter.PlaylistAdapter;
import com.ltdd.cringempone.ui.playlist.model.PlaylistItem;
import com.ltdd.cringempone.utils.Helper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlaylistActivity extends AppCompatActivity {
    private String playlistId;
    private PlaylistDTO playlist;
    private PlaylistViewHolder holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        getSupportActionBar().hide();
        playlistId = getIntent().getStringExtra("playlistId");
        Log.e("???", "onCreate: ??? " + playlistId);
        initViewHolder();
        loadData();
        addControl();

    }
    public void loadData(){
        SharedPreferences prefs = getSharedPreferences("LocalStorage", Context.MODE_PRIVATE);
        if (prefs.getString(playlistId, "").equals("")){
            playlist = new BaseAPIService.Converter<>(PlaylistDTO.class)
                    .get(BaseAPIService.getInstance().getRequest("getDetailPlaylist", playlistId));
        }
        else{
            playlist = new BaseAPIService.Converter<>(PlaylistDTO.class)
                    .get(prefs.getString(playlistId, ""));
        }
    }
    public void initViewHolder(){
        holder = new PlaylistViewHolder(
                findViewById(R.id.playlist_header_img),
                findViewById(R.id.playlist_header_title),
                findViewById(R.id.playlist_header_total_song),
                findViewById(R.id.playlist_header_total_time),
                findViewById(R.id.playlist_songList)
        );
    }
    public void addControl(){
        Picasso.get().load(playlist.thumbnailM).fit().into(holder.pImg);
        holder.pTitle.setText(playlist.title);
        holder.pTotalSong.append(String.valueOf(playlist.song.total));
        holder.pTotalTime.append(String.format("%d giờ, %d phút",
                TimeUnit.SECONDS.toHours(playlist.song.totalDuration),
                TimeUnit.SECONDS.toMinutes(playlist.song.totalDuration) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(playlist.song.totalDuration))
        ));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        PlaylistAdapter adapter = new PlaylistAdapter(playlist.song.items);

        holder.pSongList.setLayoutManager(linearLayoutManager);
        holder.pSongList.setAdapter(adapter);
    }
    class PlaylistViewHolder{
        ImageView pImg;
        TextView pTitle, pTotalSong, pTotalTime;
        RecyclerView pSongList;

        public PlaylistViewHolder(ImageView pImg, TextView pTitle, TextView pTotalSong, TextView pTotalTime, RecyclerView pSongList) {
            this.pImg = pImg;
            this.pTitle = pTitle;
            this.pTotalSong = pTotalSong;
            this.pTotalTime = pTotalTime;
            this.pSongList = pSongList;
        }
    }
}