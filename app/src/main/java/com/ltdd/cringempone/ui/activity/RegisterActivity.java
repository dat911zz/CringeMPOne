package com.ltdd.cringempone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ltdd.cringempone.R;

public class RegisterActivity extends AppCompatActivity {
    EditText txtEmail, txtPassword, txtConfirmPassword;
    Button btnSignUp, btnSignUpWithGoogle, btnSignUpWithFacebook;
    TextView txtViewSignIn;
    FirebaseAuth mAuth;
    GoogleSignInClient gsc;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(RegisterActivity.this, TestUserProfile.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth =FirebaseAuth.getInstance();
        loadControls();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        gsc = GoogleSignIn.getClient(this,options);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkInput();
                    }
                });

        //đăng ký bằng tài khoản google
        btnSignUpWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = gsc.getSignInIntent();
                startActivityForResult(intent, 1234);

            }
        });

        //đăng ký bằng tài khoản facebook
        btnSignUpWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        txtViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, TestLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkInput() {
        String email, password, repassword;
        email = txtEmail.getText().toString();
        password = txtPassword.getText().toString();
        repassword = txtConfirmPassword.getText().toString();

        //Kiểm tra dữ liệu đã nhập vào textbox hay chưa và kiểm tra password có giống nhau hay không
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Nhập email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            Toast.makeText(RegisterActivity.this, "Nhập xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.compareTo(repassword) != 0) {
            Toast.makeText(RegisterActivity.this, "Xác nhận mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
            return;
        }

        //tạo email và password vào firebase authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, TestLoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Tạo không thành công, vui lòng kiểm tra lại email và mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    Intent intent = new Intent(getApplicationContext(), TestUserProfile.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadControls(){
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        btnSignUp = (Button) findViewById(R.id.btnSIgnUp);
        btnSignUpWithGoogle = (Button) findViewById(R.id.btnSignUpWithGoogle);
        btnSignUpWithFacebook = (Button) findViewById(R.id.btnSignUpWIthFacebook);

        txtViewSignIn = (TextView) findViewById(R.id.txtViewSignIn);

    }
}