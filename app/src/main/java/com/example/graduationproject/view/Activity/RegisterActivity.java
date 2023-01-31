package com.example.graduationproject.view.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.example.graduationproject.R;
import com.example.graduationproject.model.UserData;
import com.example.graduationproject.utils.ThreadUtils;
import com.example.graduationproject.utils.TitleBar;
import com.example.graduationproject.utils.ValidatorUtils;

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
//    @BindView(R.id.et_password2)
//    EditText etPassword2;
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
        btnRegister.setOnClickListener(v -> inputRoom());
    }

    private void inputRoom() {
        if (etUsername.getText() == null || etUsername.getText().length() != 11 || !ValidatorUtils.isMobile(etUsername.getText().toString())) {
            ToastUtils.showShort("请输入正确手机号");
            return;
        }
        if (etPassword.getText() == null || !ValidatorUtils.isPassword(etPassword.getText().toString())) {
            ToastUtils.showShort("密码由字母和数字组成（6-12位）");
            return;
        }
        // TODO: 2023/1/15 数据库
        UserData userData = new UserData();
        userData.setPhone(etUsername.getText().toString());
        userData.setPassword(etPassword.getText().toString());
        ThreadUtils.insertUserData(this,userData);
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}