package com.example.graduationproject.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.example.graduationproject.R;
import com.example.graduationproject.controller.SPUtils;
import com.example.graduationproject.model.UserData;
import com.example.graduationproject.utils.ThreadUtils;
import com.example.graduationproject.utils.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_appname)
    TextView tvAppname;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_reg)
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        titleBar.setTitle("登录");
        btnLogin.setOnClickListener(v -> toLogin());
        btnReg.setOnClickListener(v -> toRegistered());
    }

    private void toLogin() {
        if (etUsername.getText() == null || etUsername.getText().length() != 11) {
            ToastUtils.showShort("手机号错误");
            return;
        }
        ThreadUtils.getUserData(this, etUsername.getText().toString(),etPassword.getText().toString());
    }

    private void toRegistered() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}