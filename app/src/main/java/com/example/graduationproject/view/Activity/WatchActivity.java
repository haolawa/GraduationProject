package com.example.graduationproject.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;
import androidx.room.Room;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.graduationproject.R;
import com.example.graduationproject.adapter.FilmListAdapter;
import com.example.graduationproject.adapter.FilmSaveAdapter;
import com.example.graduationproject.base.BaseActivity;
import com.example.graduationproject.controller.AppDatabase;
import com.example.graduationproject.controller.FilmDao;
import com.example.graduationproject.model.FilmBean;
import com.example.graduationproject.model.FilmSaveBean;
import com.example.graduationproject.utils.TitleBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WatchActivity extends BaseActivity {
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
    private List<FilmSaveBean> filmBeanList;// = new ArrayList<>();
    private FilmSaveAdapter adapter;
    private String name = "";


    @Override
    protected void initData() {
         initList();
    }
    private void initList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"myfilm.db").build();
                FilmDao filmDao = db.filmDao();
                List<FilmSaveBean> filmList = new ArrayList<>();
                filmList = filmDao.getWatch(true);
                filmBeanList.clear();
                filmBeanList.addAll(filmList);

                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(() -> {
                    adapter.notifyDataSetChanged();
                });
            }
        }).start();

    }
    @Override
    protected void initView(Bundle bundle) {
        titleBar.setTitle("已看");
        titleBar.setBackOnclickListener(this);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e("haolawa", "aa");

                //关闭刷新方法
                //refreshLayout.finishRefresh();
                // refreshLayout.finishLoadMore();

            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.e("haolawa", "bb");
            }
        });

        initAdapter();
    }

    @Override
    protected void initArgs(Intent intent) {

    }


    private void initAdapter() {
        filmBeanList = new ArrayList<>();

        adapter = new FilmSaveAdapter(R.layout.item_film_list, filmBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(WatchActivity.this, FilmDetailActivity.class);
                intent.putExtra("uid",filmBeanList.get(position).getUid());
                startActivity(intent);

            }
        });
    }


    @OnClick({R.id.et_search, R.id.img_search, R.id.recycle_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_search:
//                name = etSearch.getText().toString().trim();
//                initList();
                break;
            case R.id.img_search:
                break;
            case R.id.recycle_view:
                break;
            case R.id.refreshLayout:
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_watch;
    }
}