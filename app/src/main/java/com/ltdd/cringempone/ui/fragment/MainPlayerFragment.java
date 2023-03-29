package com.ltdd.cringempone.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ltdd.cringempone.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPlayerFragment extends Fragment {
    public MainPlayerFragment() {
        // Required empty public constructor
    }
    public static MainPlayerFragment newInstance() {
        MainPlayerFragment fragment = new MainPlayerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_player, container, false);
    }
}