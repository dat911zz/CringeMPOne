package com.ltdd.cringempone.ui.account;

import static com.ltdd.cringempone.MainActivity.REQUEST_CODE;
import static com.ltdd.cringempone.MainActivity.uri;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.ltdd.cringempone.MainActivity;
import com.ltdd.cringempone.R;
import com.ltdd.cringempone.databinding.FragmentGalleryBinding;

public class AccountFragment extends Fragment {
    View view;
    public static ImageView imageAvatar;
    EditText edtDisplayName, edtEmail;
    Button btnUpdate;
    MainActivity m;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        m = (MainActivity) getActivity();
        progressDialog = new ProgressDialog(getActivity());
        addControls();
        setUserInformation();
        initListener();
        return view;
    }



    private void addControls()
    {
        imageAvatar = (ImageView) view.findViewById(R.id.fragment_account_img_avatar);
        edtDisplayName = view.findViewById(R.id.fragment_account_edt_display_name);
        edtEmail = view.findViewById(R.id.fragment_account_edt_email);
        btnUpdate = view.findViewById(R.id.fragment_account_btn_update_profile);

    }
    public void setUserInformation()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null)
        {
            return;
        }
        edtDisplayName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.avatar_default).into(imageAvatar);
    }
    public void initListener()
    {
        imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();



            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile();
            }
        });

    }
    public void onClickRequestPermission()
    {
        if(m == null)
        {
            return;
        }

        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            m.openGallery();

        } else {
            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissions, REQUEST_CODE);
        }
    }

    public void onClickUpdateProfile()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null)
        {
            return;
        }
        progressDialog.show();
        String name = edtDisplayName.getText().toString().trim();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            m.showUserInformation();

                        }
                    }
                });
    }





}