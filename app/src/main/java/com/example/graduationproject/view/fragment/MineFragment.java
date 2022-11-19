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
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @OnClick({R.id.img_avatar, R.id.tv_username, R.id.ll_version, R.id.btn_out_login})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_avatar:
                break;
            case R.id.tv_username:
                break;
            case R.id.ll_version:
                break;
            case R.id.btn_out_login:
                intent = new Intent(getContext(), LoginActivity.class);
                Log.e("haolawa", "onClick: ");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}