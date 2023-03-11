package com.ltdd.cringempone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.ltdd.cringempone.components.MusicPlayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Sys", "onCreate: ok");
        setContentView(R.layout.activity_main);
        Button player = findViewById(R.id.btnMusicPlayer);
        player.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MusicPlayer.class);
            startActivity(intent);
        });
    }
}