package com.ltdd.cringempone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    CheckBox showPasswordCheckBox;
    TextView backTextView;
    EditText passwordEditText;
    EditText emailEditText;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        addControls();
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
                if (email.equals("email") && password.equals("password")) {
                    // Đúng, đăng nhập thành công và chuyển sang activity trang chủ
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Sai, yêu cầu nhập lại
                    Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void addControls()
    {
        showPasswordCheckBox = (CheckBox) findViewById(R.id.showPasswordCheckBox);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        backTextView = (TextView) findViewById(R.id.backTextView);

    }
}