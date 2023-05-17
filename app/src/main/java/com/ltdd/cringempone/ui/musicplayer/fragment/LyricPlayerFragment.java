package com.ltdd.cringempone.ui.musicplayer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.api.*;
import com.ltdd.cringempone.data.dto.LyricDTO;
import com.ltdd.cringempone.databinding.FragmentLyricPlayerBinding;
import com.ltdd.cringempone.service.LocalStorageService;
import com.ltdd.cringempone.service.MediaControlReceiver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LyricPlayerFragment extends Fragment {
    FragmentLyricPlayerBinding binding;
    public LyricPlayerFragment() {}
    public static LyricPlayerFragment newInstance() {
        return new LyricPlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLyricPlayerBinding.inflate(inflater, container, false);
        addControl();
        return binding.getRoot();
    }
    private void addControl(){
        ArrayList<String> lyrics = new ArrayList<>();
        binding.fragmentLyricPlayerListview.setAdapter(
                new ArrayAdapter<>(
                        this.getContext(),
                        R.layout.fragment_lyric_player_list_item_layout,
                        R.id.fragment_lyric_list_item_list_content,
                        fetchLyricData())
        );
    }
    private List<String> fetchLyricData(){
        String res = LocalStorageService.getInstance().getString("lrc_" + MediaControlReceiver.getInstance().getCurrentSong().encodeId);
        if (res.contains("error") || res.equals("")){
            res = BaseAPIService.getInstance().getRequest(
                    "getLyric",
                    MediaControlReceiver.getInstance().getCurrentSong().encodeId
            );
            LocalStorageService.getInstance().putString("lrc_" + MediaControlReceiver.getInstance().getCurrentSong().encodeId, res);
        }
        return convertRes2Sentences(res);
    }
    private List<String> convertRes2Sentences(String res){
        List<String> sentences = new ArrayList<>();

        new BaseAPIService.Converter<>(LyricDTO.class).get(res).sentences.forEach(sentence -> {
            String result = "";
            for(LyricDTO.Word word : sentence.words){
                result += " " + word.data;
            }
            sentences.add(result);
//            Log.e("App", "convertRes2Sentences: " + result);
        });

        return sentences;
    }
}