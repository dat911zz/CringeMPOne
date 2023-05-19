package com.ltdd.cringempone.ui.musicplayer;

import com.ltdd.cringempone.api.BaseAPIService;

import android.os.Handler;
import android.widget.ArrayAdapter;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.data.dto.GenreDTO;
import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.ltdd.cringempone.databinding.FragmentInfoPlayerBinding;
import com.ltdd.cringempone.databinding.FragmentLyricPlayerBinding;
import com.ltdd.cringempone.databinding.FragmentMainPlayerBinding;
import com.ltdd.cringempone.service.LocalStorageService;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.musicplayer.fragment.LyricPlayerFragment;
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

    public void loadDataIntoFragments(ItemDTO songInfo){
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
        String date = df.format(new Date(new Timestamp(Long.parseLong(songInfo.releaseDate) * 1000L).getTime()));// Convert epoch time to timeStamp
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerSongName.setText(songInfo.title);
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerArtists.setText(songInfo.artistsNames);
        Picasso.get().load(songInfo.thumbnailM).fit().into(fragmentInfoPlayerBinding.fragmentInfoPlayerContainerImg);
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerAlbumTxt.setText(songInfo.isAlbum ? "" : songInfo.title);
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerArtistsTxt.setText(songInfo.artistsNames);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String res = LocalStorageService.getInstance().getString("sif_" + songInfo.encodeId);
//                if (res.contains("error") || res.equals("")){
//                    res = BaseAPIService.getInstance().getRequest("getCategoryMV", songInfo.genreIds.get(0));
//                    LocalStorageService.getInstance().putString("sif_" + songInfo.encodeId, res);
//                }
//                GenreDTO grs = new BaseAPIService.Converter<>(GenreDTO.class).get(res);
//                String genres = "";
//                for(GenreDTO.Child genreDTO : grs.childs){
//                    if (songInfo.genreIds.get(1).equals(genreDTO.id)){
//                        genres += genreDTO.name + ", ";
//                    }
//                }
//                genres = genres.substring(0, genres.length() - 2);
//                fragmentInfoPlayerBinding.fragmentInfoPlayerContainerTypeTxt.setText(genres);
//            }
//        }, 50);


        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerReleaseTimeTxt.setText(date);
        fragmentInfoPlayerBinding.fragmentInfoPlayerContainerDistributorTxt.setText(songInfo.distributor);
    }

}
