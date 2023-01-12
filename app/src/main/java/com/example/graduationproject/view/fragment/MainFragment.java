package com.example.graduationproject.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.graduationproject.R;
import com.example.graduationproject.base.BaseFragment;
import com.example.graduationproject.utils.ThreadUtils;
import com.example.graduationproject.utils.TitleBar;
import com.example.graduationproject.view.Activity.AddFilmActivity;
import com.example.graduationproject.view.Activity.FilmListCommonActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class MainFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_film_count)
    TextView tvFilmCount;
    @BindView(R.id.ll_watch)
    LinearLayout llWatch;
    @BindView(R.id.ll_un_watch)
    LinearLayout llUnWatch;
    @BindView(R.id.btn_add)
    Button btnAdd;


    @Override
    protected void initView(Bundle bundle) {
        titleBar.setTitle("主页");
        ThreadUtils.filmCount(getContext(),tvFilmCount);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }


    @Override
    public void onResume() {
        super.onResume();
        ThreadUtils.filmCount(getContext(),tvFilmCount);
    }

    @Override
    @OnClick({R.id.ll_watch, R.id.ll_un_watch, R.id.btn_add})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_watch:
                intent = new Intent(getContext(), FilmListCommonActivity.class);
                intent.putExtra("title","已看");
                startActivity(intent);
                break;
            case R.id.ll_un_watch:
                intent = new Intent(getContext(), FilmListCommonActivity.class);
                intent.putExtra("title","未看");
                startActivity(intent);
                break;
            case R.id.btn_add:
                intent = new Intent(getContext(), AddFilmActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}