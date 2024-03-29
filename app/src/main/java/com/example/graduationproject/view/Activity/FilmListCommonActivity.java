package com.example.graduationproject.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationproject.R;
import com.example.graduationproject.adapter.FilmSaveAdapter;
import com.example.graduationproject.base.BaseActivity;
import com.example.graduationproject.model.FilmSaveBean;
import com.example.graduationproject.utils.ThreadUtils;
import com.example.graduationproject.utils.TitleBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FilmListCommonActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private List<FilmSaveBean> filmBeanList = new ArrayList<>();
    private FilmSaveAdapter adapter;
    private String title;

    @Override
    protected void initArgs(Intent intent) {
        title = intent.getStringExtra("title");
    }

    @Override
    protected void initView(Bundle bundle) {
        titleBar.setTitle(title);
        titleBar.setBackOnclickListener(this);

        //关闭刷新方法
        smartRefreshLayout.setOnRefreshListener(RefreshLayout::finishRefresh);
        smartRefreshLayout.setOnLoadMoreListener(RefreshLayout::finishLoadMore);
        imgSearch.setOnClickListener(v -> {
            ThreadUtils.filmSearch(this, etSearch.getText().toString(), filmBeanList, adapter);
        });

        initAdapter();
    }

    @Override
    protected void initData() {
        getData();
    }

    private void initAdapter() {
        adapter = new FilmSaveAdapter(filmBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(FilmListCommonActivity.this, FilmDetailActivity.class);
            intent.putExtra("uid", filmBeanList.get(position).getUid());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        if (title.equals("未看")) {
            ThreadUtils.filmList(getApplicationContext(), filmBeanList, adapter, ThreadUtils.isUnWatch);
        } else {
            ThreadUtils.filmList(getApplicationContext(), filmBeanList, adapter, ThreadUtils.isWatch);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_film_list;
    }

}