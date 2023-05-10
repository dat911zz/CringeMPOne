package com.ltdd.cringempone.ui.musicplayer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ltdd.cringempone.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LyricPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LyricPlayerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String LYRIC = "lyric";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LyricPlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LyricPlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LyricPlayerFragment newInstance() {
        LyricPlayerFragment fragment = new LyricPlayerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lyric_player, container, false);
    }
}