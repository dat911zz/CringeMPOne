package com.ltdd.cringempone.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;

import com.ltdd.cringempone.R;

public class CustomsDialog {
    public static ProgressDialog pgl;
    public static void showLoadingDialog(Context context){
        pgl = new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
        pgl.setMessage("Vui lòng chờ trong giây lát...");
        pgl.setCancelable(true);
        pgl.show();
        pgl.setIndeterminate(true);
        pgl.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.progress_bar_handler));
    }
    public static void showOptionalDialog(Context context, String title, String message){
        pgl = ProgressDialog.show(context, title, message, false, true);
    }
    public static void showAlertDialog(Context context, String title, String message, int iconID){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(iconID)
                .show();
    }
    public static void hideDialog(){
        if (pgl != null){
            pgl.dismiss();
        }
    }
}
