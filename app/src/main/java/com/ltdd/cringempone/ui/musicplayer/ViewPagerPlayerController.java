package com.ltdd.cringempone.ui.musicplayer;

import com.ltdd.cringempone.api.BaseAPIService;

import android.widget.ArrayAdapter;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.data.dto.GenreDTO;
import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.ltdd.cringempone.databinding.FragmentInfoPlayerBinding;
import com.ltdd.cringempone.databinding.FragmentLyricPlayerBinding;
import com.ltdd.cringempone.databinding.FragmentMainPlayerBinding;
import com.ltdd.cringempone.service.LocalStorageService;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewPagerPlayerController {
    private static ViewPagerPlayerController instance;
    FragmentLyricPlayerBinding fragmentLyricPlayerBinding;
    FragmentMainPlayerBinding fragmentMainPlayerBinding;
    FragmentInfoPlayerBinding fragmentInfoPlayerBinding;

    public FragmentLyricPlayerBinding getFragmentLyricPlayerBinding() {
        return fragmentLyricPlayerBinding;
    }

    public FragmentMainPlayerBinding getFragmentMainPlayerBinding() {
        return fragmentMainPlayerBinding;
    }

    public FragmentInfoPlayerBinding getFragmentInfoPlayerBinding() {
        return fragmentInfoPlayerBinding;
    }

    public void setFragmentLyricPlayerBinding(FragmentLyricPlayerBinding fragmentLyricPlayerBinding) {
        this.fragmentLyricPlayerBinding = fragmentLyricPlayerBinding;
    }

    public void setFragmentMainPlayerBinding(FragmentMainPlayerBinding fragmentMainPlayerBinding) {
        this.fragmentMainPlayerBinding = fragmentMainPlayerBinding;
    }

    public void setFragmentInfoPlayerBinding(FragmentInfoPlayerBinding fragmentInfoPlayerBinding) {
        this.fragmentInfoPlayerBinding = fragmentInfoPlayerBinding;
    }


    private ViewPagerPlayerController(){}
    public static ViewPagerPlayerController getInstance(){
        if (instance == null){
            instance = new ViewPagerPlayerController();
        }
        return instance;
    }

    public void loadDataIntoFragments(String songId){

        String res = LocalStorageService.getInstance().getString("sinfo_" + songId);
        if (res.equals("") || res.contains("error")){
            res = BaseAPIService.getInstance().getRequest("getSongInfo", songId);
            LocalStorageService.getInstance().putString("sinfo_" + songId, res);
        }
        SongInfoDTO songInfo = new BaseAPIService.Converter<>(SongInfoDTO.class).get(res);
        //MainPlayer
        fragmentMainPlayerBinding.txtTitle.setText(songInfo.artistsNames);
        fragmentMainPlayerBinding.txtSongName.setText(songInfo.title);
        MediaControlReceiver.getInstance().executeDisc(fragmentMainPlayerBinding.imgDisc);
        //Lyric
        fragmentLyricPlayerBinding.fragmentLyricPlayerListview.setAdapter(
                new ArrayAdapter<>(
                        fragmentLyricPlayerBinding.getRoot().getContext(),
                        R.layout.fragment_lyric_player_list_item_layout,
                        R.id.fragment_lyric_list_item_list_content,
                        BaseAPIService.getInstance().fetchLyricData())
        );
        //Info
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mm aa");
        String date = df.format(new Date(new Timestamp(songInfo.releaseDate * 1000L).getTime()));// Convert epoch time to timeStamp
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerSongName.setText(songInfo.title);
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerArtists.setText(songInfo.artistsNames);
        Picasso.get().load(songInfo.thumbnailM).fit().into(fragmentInfoPlayerBinding.fragmentInfoPlayerContainerImg);
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerAlbumTxt.setText(songInfo.album.title);
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerArtistsTxt.setText(songInfo.artistsNames);
        String genres = "";
        for (int i = 0; i < songInfo.genres.size(); i++) {
            genres += songInfo.genres.get(i).name;
            if(i != songInfo.genres.size() - 1){
                genres += ", ";
            }
        }
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerGenreTxt.setText(genres);
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerReleaseTimeTxt.setText(date);
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerDistributorTxt.setText(songInfo.distributor);
    }

}
