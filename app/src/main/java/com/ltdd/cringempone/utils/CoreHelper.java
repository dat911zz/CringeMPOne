package com.ltdd.cringempone.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.SongInfoDTO;

import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.net.URL;

public class CoreHelper {
    public static class ImageUtil{
        public static Drawable LoadImageFromWebOperations(String url) {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (Exception e) {
                Log.e("CoreHelper", "LoadImageFromWebOperations: " + e.getMessage());
                return null;
            }
        }
    }

    public static class FragmentUtil{

        public static FragmentTransaction ft;
        public static void addFragment(Activity activity, Fragment fragment, int frameLayout,@Nullable String name){
            FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
            FragmentTransaction fr = fragmentManager.beginTransaction();
                    fr.replace(frameLayout, fragment)
                    .setReorderingAllowed(true);
            if (name != null) {
                fr.addToBackStack(name); // name can be null
            }
            fr.commit();
        }
    }
    public static class MediaProgressDialog{
        public static ProgressDialog pgl;
        public static void showDialog(Context context){
            pgl = ProgressDialog.show(context, "Đang tải", "Vui lòng chờ trong giây lát...", true);
        }
        public static void hideDialog(){
            if (pgl != null){
                pgl.dismiss();
            }
        }
    }
    public static class Converter{
        public static ItemDTO songInfoDTO2ItemDTO(SongInfoDTO songInfoDTO){
            return new ItemDTO(
                    songInfoDTO.encodeId,
                    songInfoDTO.title,
                    songInfoDTO.thumbnail,
                    songInfoDTO.isOffical,
                    songInfoDTO.link,
                    songInfoDTO.isIndie,
                    "",
                    "",
                    0,
                    songInfoDTO.genreIds,
                    songInfoDTO.preRelease,
                    songInfoDTO.artists,
                    songInfoDTO.artistsNames,
                    0,
                    0,
                    songInfoDTO.userid,
                    songInfoDTO.thumbnailM,
                    false,
                    songInfoDTO.isPrivate,
                    songInfoDTO.username,
                    false,
                    "",
                    false,
                    songInfoDTO.distributor,
                    songInfoDTO.hasLyric,
                    songInfoDTO.mvlink
            );
        }
    }

}
