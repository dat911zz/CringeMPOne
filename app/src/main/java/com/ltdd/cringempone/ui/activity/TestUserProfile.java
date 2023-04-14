package com.ltdd.cringempone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ltdd.cringempone.LoginActivity;
import com.ltdd.cringempone.R;

public class TestUserProfile extends AppCompatActivity {

    FirebaseAuth auth;
    Button button;
    TextView txtViewUserEmail, txtViewUserName;
    FirebaseUser user;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_test);



        auth = FirebaseAuth.getInstance();
        button =(Button) findViewById(R.id.btnLogout);
        txtViewUserEmail =(TextView) findViewById(R.id.txtViewUserEmail);
        txtViewUserName =(TextView) findViewById(R.id.txtViewUserName);
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


        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(TestUserProfile.this, options);

        //Đăng xuất khỏi tài khoản
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gsc.signOut();//Đăng xuất khỏi google
                FirebaseAuth.getInstance().signOut();//Đăng xuất khỏi Firebase Auth
                Intent intent = new Intent(TestUserProfile.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}