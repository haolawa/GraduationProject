package com.example.graduationproject.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.graduationproject.R;
import com.example.graduationproject.adapter.FilmSaveAdapter;
import com.example.graduationproject.base.BaseFragment;
import com.example.graduationproject.controller.AppDatabase;
import com.example.graduationproject.controller.FilmDao;
import com.example.graduationproject.model.FilmSaveBean;
import com.example.graduationproject.utils.TitleBar;
import com.example.graduationproject.view.Activity.FilmDetailActivity;
import com.example.graduationproject.view.Activity.WatchActivity;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CollectionFragment extends Fragment implements View.OnClickListener {
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
    private View rootView;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_collection, container, false);

        }
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;


    }

    private void initView() {
        //titleBar = rootView.findViewById(R.id.title_bar);
        titleBar.setTitle("收藏");
        initList();
        initAdapter();
    }
    private void initList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),AppDatabase.class,"myfilm.db").build();
                FilmDao filmDao = db.filmDao();
                List<FilmSaveBean> filmList = new ArrayList<>();
                filmList = filmDao.getLove(true);
                filmBeanList.clear();
                filmBeanList.addAll(filmList);

                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(() -> {
                    adapter.notifyDataSetChanged();
                });


            }
        }).start();

    }
    private void initAdapter() {
        filmBeanList = new ArrayList<>();

        adapter = new FilmSaveAdapter(R.layout.item_film_list, filmBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
                intent.putExtra("uid",filmBeanList.get(position).getUid());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @OnClick({R.id.et_search, R.id.img_search, R.id.recycle_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_search:
                break;
            case R.id.img_search:
                break;
            case R.id.recycle_view:
                break;
            case R.id.refreshLayout:
                break;
        }
    }

}