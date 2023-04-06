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
    EditText usernameEditText;
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
        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && usernameEditText.getText().length() == 0) {
                    // Hiển thị lại hint nếu EditText trống
                    usernameEditText.setHint("Username");
                } else {
                    // Ẩn hint khi EditText nhận được sự tương tác (được chọn)
                    usernameEditText.setHint("");
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Kiểm tra thông tin tài khoản
                if (username.equals("username") && password.equals("password")) {
                    // Đúng, đăng nhập thành công và chuyển sang activity trang chủ
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Sai, yêu cầu nhập lại
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void addControls()
    {
        showPasswordCheckBox = (CheckBox) findViewById(R.id.showPassword);
        passwordEditText = (EditText) findViewById(R.id.password);
        usernameEditText = (EditText) findViewById(R.id.username);
        loginButton = (Button) findViewById(R.id.login);
        backTextView = (TextView) findViewById(R.id.backTextView);

    }
}