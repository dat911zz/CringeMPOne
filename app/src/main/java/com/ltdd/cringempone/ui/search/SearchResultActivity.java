package com.ltdd.cringempone.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.data.dto.SearchDTO;
import com.ltdd.cringempone.databinding.ActivitySearchResultBinding;
import com.ltdd.cringempone.ui.search.adapter.SearchPlaylistAdapter;
import com.ltdd.cringempone.ui.search.adapter.SongAdapter;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    ActivitySearchResultBinding binding;
    SearchDTO searchResult;
    SongViewHolder holder;

    RecyclerView searchResultRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        addControl();

    }
    public void loadData(String txtSearch){
        searchResult = BaseAPIService.getInstance().getSearchResult(txtSearch);
    }
    public void initViewHolder(){
        holder = new SongViewHolder(binding.searchResultRecycleview);
    }
    public void searchAction(){
        SongAdapter songAdapter = new SongAdapter(searchResult.songs);
        SearchPlaylistAdapter playlistAdapter = new SearchPlaylistAdapter(searchResult.playlists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        holder.songList.setLayoutManager(linearLayoutManager);
        holder.songList.setAdapter(playlistAdapter);

    }
    public void addControl()
    {
        binding.btnSearchBack.setOnClickListener(v -> onBackPressed());
        binding.btnSearch.setOnClickListener(v -> {
            if (binding.txtSearch.getText().toString().equals(""))
            {
                Toast.makeText(this, "Vui lòng nhập dữ liệu cần tìm kiếm", Toast.LENGTH_SHORT).show();
            }
            else {
                initViewHolder();
                loadData(binding.txtSearch.getText().toString());
                searchAction();
            }
        });
        binding.btnMic.setOnClickListener(v -> {
            displaySpeechRecognizer();
        });
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
                searchAction();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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
    private static final int SPEECH_REQUEST_CODE = 0;

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            binding.txtSearch.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}