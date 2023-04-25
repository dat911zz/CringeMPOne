package com.ltdd.cringempone;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ltdd.cringempone.ui.activity.RegisterActivity;
import com.ltdd.cringempone.ui.activity.TestUserProfile;

public class LoginActivity extends AppCompatActivity {

    TextView txtViewSignUp;
    CheckBox showPasswordCheckBox;
    TextView backTextView;
    EditText passwordEditText;
    EditText emailEditText;
    Button loginButton, btnLoginGoogle, btnLoginFacebook;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LoginActivity.this, TestUserProfile.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        addControls();
        mAuth =FirebaseAuth.getInstance();
        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Hiện mật khẩu
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    // Ẩn mật khẩu
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && passwordEditText.getText().length() == 0) {
                    // Hiển thị lại hint nếu EditText trống
                    passwordEditText.setHint("Password");
                } else {
                    // Ẩn hint khi EditText nhận được sự tương tác (được chọn)
                    passwordEditText.setHint("");
                }
            }
        });
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && emailEditText.getText().length() == 0) {
                    // Hiển thị lại hint nếu EditText trống
                    emailEditText.setHint("Email");
                } else {
                    // Ẩn hint khi EditText nhận được sự tương tác (được chọn)
                    emailEditText.setHint("");
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Kiểm tra thông tin tài khoản
                if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)) {
                    // Đúng, đăng nhập thành công và chuyển sang activity trang chủ
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Đăng nhập bằng email và mật khẩu
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //đăng nhập thành công thì chuyển activity
                                    Toast.makeText(LoginActivity.this, "Login successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, TestUserProfile.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Email or Password is not correct",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        txtViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addControls()
    {
        showPasswordCheckBox = (CheckBox) findViewById(R.id.showPasswordCheckBox);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        backTextView = (TextView) findViewById(R.id.backTextView);
        txtViewSignUp = (TextView) findViewById(R.id.txtViewSignUp);
        btnLoginGoogle = (Button) findViewById(R.id.googleButton);
        btnLoginFacebook =(Button) findViewById(R.id.facebookButton);

    }
}