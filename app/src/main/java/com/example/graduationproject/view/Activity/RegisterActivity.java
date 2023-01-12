package com.example.graduationproject.view.Activity;

import android.annotation.SuppressLint;
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
@SuppressLint("NonConstantResourceId")
public class RegisterActivity extends AppCompatActivity {

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
    @BindView(R.id.et_password2)
    EditText etPassword2;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initView();
    }
    private  void initView(){
        titleBar.setBackOnclickListener(this);
        titleBar.setTitle("注册");
    }

    @OnClick({R.id.et_username, R.id.et_password, R.id.et_password2, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_username:
                break;
            case R.id.et_password:
                break;
            case R.id.et_password2:
                break;
            case R.id.btn_register:
                break;
            default:
                break;
        }
    }
}