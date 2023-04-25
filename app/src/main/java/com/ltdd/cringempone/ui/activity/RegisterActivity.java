package com.ltdd.cringempone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ltdd.cringempone.R;

public class RegisterActivity extends AppCompatActivity {
    EditText txtEmail, txtPassword, txtConfirmPassword, txtUserName;
    Button btnSignUp, btnSignUpWithGoogle, btnSignUpWithFacebook;
    CheckBox showPasswordCheckBox;
    TextView txtViewSignIn, txtViewBack;
    FirebaseAuth mAuth;
    GoogleSignInClient gsc;
    DatabaseReference userDbRef;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();}

        addControls();
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth =FirebaseAuth.getInstance();

        //tạo cửa sổ google sign in để chọn tài khoản
        createGoogleSignWindow();

        showPasswordOnCheck();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        signUp();
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
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void createGoogleSignWindow() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(RegisterActivity.this,options);
    }

    private void showPasswordOnCheck() {
        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Hiện mật khẩu
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    txtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    // Ẩn mật khẩu
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    txtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }


    private void signUp() {
        String email = txtEmail.getText().toString();
        String userName = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();
        String repassword = txtConfirmPassword.getText().toString();

        //Kiểm tra dữ liệu đã nhập vào textbox hay chưa và kiểm tra password có giống nhau hay không
        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(userName)||TextUtils.isEmpty(password)||TextUtils.isEmpty(repassword)) {
            Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (TextUtils.isEmpty(password)) {
//            Toast.makeText(RegisterActivity.this, "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(repassword)) {
//            Toast.makeText(RegisterActivity.this, "Nhập xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (password.compareTo(repassword) != 0) {
            Toast.makeText(RegisterActivity.this, "Xác nhận mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
            return;
        }

        //Đăng ký email và password vào firebase authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Tạo tài khoản thành công thì trở về màn hình đăng nhập
                            Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                            //Thêm dử liệu users vào realtime
                            UsersTest users = new UsersTest(email,userName);
                            userDbRef.push().setValue(users);
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            FirebaseAuth.getInstance().signOut();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Tạo không thành công, vui lòng kiểm tra lại email và mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkInput(String email, String userName, String password, String repassword) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1234){
            //Đăng ký tài khoản google vào firebase authentication bằng cách lấy IdToken
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
                                    //Thêm dử liệu users vào Realtime
                                    UsersTest users = new UsersTest(currentUser.getEmail(),currentUser.getDisplayName());
                                    userDbRef.push().setValue(users);

                                    Intent intent = new Intent(RegisterActivity.this, TestUserProfile.class);
                                    startActivity(intent);
                                    finish();
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

    private void addControls(){
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        txtUserName =(EditText) findViewById(R.id.txtUserName);
        btnSignUp = (Button) findViewById(R.id.btnSIgnUp);
        btnSignUpWithGoogle = (Button) findViewById(R.id.btnSignUpWithGoogle);
        btnSignUpWithFacebook = (Button) findViewById(R.id.btnSignUpWIthFacebook);
        txtViewBack = (TextView)findViewById(R.id.txtViewBack);
        showPasswordCheckBox = (CheckBox) findViewById(R.id.showPasswordCheckBox);
        txtViewSignIn = (TextView) findViewById(R.id.txtViewSignIn);

    }
}