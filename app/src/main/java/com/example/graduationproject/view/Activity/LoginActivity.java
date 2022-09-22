package com.example.graduationproject.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduationproject.R;
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

    private void initView(){
        titleBar.setTitle("登录");
    }

    @OnClick({R.id.et_username, R.id.et_password, R.id.btn_login, R.id.btn_reg})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.et_username:
                break;
            case R.id.et_password:
                break;
            case R.id.btn_login:
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_reg:
                intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}