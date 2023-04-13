package com.ltdd.cringempone.ui.top100;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.exoplayer2.Player;
import com.ltdd.cringempone.R;
import com.ltdd.cringempone.databinding.FragmentTop100Binding;
import com.ltdd.cringempone.ui.musicplayer.PlayerActivity;

public class Top100Fragment extends Fragment {
    private FragmentTop100Binding binding;

    public static Top100Fragment newInstance() {
        return new Top100Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top100, container, false);
        Button btn = view.findViewById(R.id.btn_goto_player);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(view.getContext(), PlayerActivity.class);
                startActivity(it);
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}