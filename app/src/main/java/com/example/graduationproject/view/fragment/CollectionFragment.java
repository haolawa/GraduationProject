package com.example.graduationproject.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.ImageView;

import com.example.graduationproject.R;
import com.example.graduationproject.adapter.FilmSaveAdapter;
import com.example.graduationproject.base.BaseFragment;
import com.example.graduationproject.model.FilmSaveBean;
import com.example.graduationproject.utils.ThreadUtils;
import com.example.graduationproject.utils.TitleBar;
import com.example.graduationproject.view.Activity.FilmDetailActivity;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectionFragment extends BaseFragment{
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

    @Override
    protected void initView(Bundle bundle) {
        titleBar.setTitle("收藏");
        initAdapter();
        ThreadUtils.filmList(getActivity().getApplicationContext(),filmBeanList,adapter,ThreadUtils.isLove);
        imgSearch.setOnClickListener(v -> {
            ThreadUtils.filmSearch(getActivity(),etSearch.getText().toString(),filmBeanList,adapter);
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_film_list;
    }

    private void initAdapter() {
        adapter = new FilmSaveAdapter(filmBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
            intent.putExtra("uid",filmBeanList.get(position).getUid());
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ThreadUtils.filmList(getActivity().getApplicationContext(),filmBeanList,adapter,ThreadUtils.isLove);
    }

}