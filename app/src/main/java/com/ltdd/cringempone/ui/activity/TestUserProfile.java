package com.ltdd.cringempone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ltdd.cringempone.MainActivity;
import com.ltdd.cringempone.R;

public class TestUserProfile extends AppCompatActivity {

    FirebaseAuth auth;
    Button button;
    TextView txtViewUserEmail, txtViewUserName;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_test);



        auth = FirebaseAuth.getInstance();
        button =(Button) findViewById(R.id.btnLogout);
        txtViewUserEmail =(TextView) findViewById(R.id.txtViewUserEmail);
        txtViewUserName =(TextView) findViewById(R.id.txtViewUserName);

        //Lấy thông tin tài khoản đang đăng nhập
        user = auth.getCurrentUser();

        if(user==null){
            Intent intent = new Intent(TestUserProfile.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            txtViewUserEmail.setText(user.getEmail());
            txtViewUserName.setText(user.getDisplayName());
        }

        //Đăng xuất khỏi tài khoản
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();//Đăng xuất khỏi facebook
                FirebaseAuth.getInstance().signOut();//Đăng xuất khỏi Firebase Auth
                Intent intent = new Intent(TestUserProfile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}