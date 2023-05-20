package com.ltdd.cringempone.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.PlaylistDTO;
import com.ltdd.cringempone.data.dto.SearchDTO;
import com.ltdd.cringempone.data.dto.SearchPlaylistDTO;
import com.ltdd.cringempone.databinding.ActivitySearchResultBinding;
import com.ltdd.cringempone.service.LocalStorageService;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.musicplayer.adapter.ParentItemAdapter;
import com.ltdd.cringempone.ui.musicplayer.model.ChildItem;
import com.ltdd.cringempone.ui.musicplayer.model.ParentItem;
import com.ltdd.cringempone.ui.playlist.PlaylistActivity;
import com.ltdd.cringempone.ui.playlist.adapter.PlaylistAdapter;
import com.ltdd.cringempone.ui.search.adapter.SearchPlaylistAdapter;
import com.ltdd.cringempone.ui.search.adapter.SongAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchResult extends AppCompatActivity {

    ActivitySearchResultBinding binding;
    SearchDTO searchResult;
    SongViewHolder holder;

    RecyclerView searchResultRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
    public void loadData(String txtSearch){
        searchResult = BaseAPIService.getInstance().getSearchResult(txtSearch);
    }
    public void initViewHolder(){
        holder = new SongViewHolder(binding.searchResultRecycleview);
    }
    public void addControl(){

        SongAdapter songAdapter = new SongAdapter(searchResult.songs);
        SearchPlaylistAdapter playlistAdapter = new SearchPlaylistAdapter(searchResult.playlists);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        holder.songList.setLayoutManager(linearLayoutManager);
        holder.songList.setAdapter(playlistAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search_menuitem);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                initViewHolder();
                loadData(query);
                addControl();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                initViewHolder();
                loadData(newText);
                addControl();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    class SongViewHolder
    {
        RecyclerView songList;
        public SongViewHolder(RecyclerView songList) {
            this.songList = songList;
        }
    }

}