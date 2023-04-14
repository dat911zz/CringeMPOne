package com.ltdd.cringempone.ui.musicplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.ltdd.cringempone.R;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.utils.Helper;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPlayerFragment extends Fragment {

    private static final String SONG_NAME = "songName";
    private static final String ARTIST = "artist";
    private String songName;
    private String artist;
    private View view;
    public MainPlayerFragment() {
        // Required empty public constructor
    }
    public static MainPlayerFragment newInstance(String songName, String artist) {
        MainPlayerFragment fragment = new MainPlayerFragment();
        Bundle args = new Bundle();
        args.putString(SONG_NAME, songName);
        args.putString(ARTIST, artist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            songName = getArguments().getString(SONG_NAME);
            artist = getArguments().getString(ARTIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_player, container, false);
        MediaControlReceiver mediaControlReceiver = MediaControlReceiver.getInstance();
        ImageView disc = view.findViewById(R.id.imgDisc);
        mediaControlReceiver.executeDisc(view.getContext(), disc);
        addControl(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void addControl(View view){
        TextView s_name = view.findViewById(R.id.txtSongName);
        TextView s_artist = view.findViewById(R.id.txtTitle);

        s_name.setText(songName);
        s_artist.setText(artist);
    }
}