package com.example.graduationproject.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.graduationproject.R;
import com.example.graduationproject.adapter.FilmListAdapter;
import com.example.graduationproject.base.BaseActivity;
import com.example.graduationproject.model.FilmBean;
import com.example.graduationproject.utils.Constants;
import com.example.graduationproject.utils.LoadingPointView;
import com.example.graduationproject.utils.TitleBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FilmListActivity extends BaseActivity {
    @BindView(R.id.id_loading_point_view)
    LoadingPointView loadingPointView;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_loading_point_view)
    LinearLayout linearLayout;
    private List<FilmBean> filmBeanList;
    private FilmListAdapter adapter;
    private String name;
    private int mPage = 1;
    private static final int RESULT_CODE = 102;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_film_list;
    }

    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView(Bundle bundle) {
        titleBar.setTitle("找到的电影");
        titleBar.setBackOnclickListener(this);
        name = getIntent().getStringExtra("name");
        //关闭刷新和上拉加载
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        //linearLayout.setVisibility(View.VISIBLE);
        getData();

        initAdapter();

    }

    @Override
    protected void initData() {

    }

    private void getData() {

        if (!name.equals("")) {
            String url = Constants.IMDB_SEARCHMOVIE + "/" + Constants.IMDBKEY + "/" + name;
            imdbSearch(url);
        }
    }

    private void imdbSearch(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(FilmListActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
//                    MainThread
//                    loadingPointView.onDetachedFromWindow();
//                    linearLayout.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = JSON.parseObject(response.body().string());
                        String data = jsonObject.getString("results");
                        List<FilmBean> filmBeans = JSONObject.parseArray(data, FilmBean.class);
//                        if (mPage == 1) {
//                            filmBeanList.clear();
//                        }

                        adapter.setList(filmBeans);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initAdapter() {
        adapter = new FilmListAdapter(R.layout.item_film_list, filmBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(FilmListActivity.this, AddFilmActivity.class);
            intent.putExtra("id", adapter.getItemId(position));
            setResult(RESULT_CODE, intent);
            finish();
        });
    }
}