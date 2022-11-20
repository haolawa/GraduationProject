package com.example.graduationproject.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.graduationproject.R;
import com.example.graduationproject.base.BaseFragment;
import com.example.graduationproject.utils.TitleBar;
import com.example.graduationproject.view.Activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MineFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_version)
    LinearLayout llVersion;
    @BindView(R.id.btn_out_login)
    Button btnOutLogin;

    @Override
    protected void initView(Bundle bundle) {
        titleBar.setTitle("我的");
        btnOutLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

}