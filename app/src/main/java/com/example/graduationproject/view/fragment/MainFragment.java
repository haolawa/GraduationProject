package com.example.graduationproject.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.blankj.utilcode.util.StringUtils;
import com.example.graduationproject.R;
import com.example.graduationproject.controller.AppDatabase;
import com.example.graduationproject.controller.FilmDao;
import com.example.graduationproject.model.FilmSaveBean;
import com.example.graduationproject.utils.TitleBar;
import com.example.graduationproject.view.Activity.AddFilmActivity;
import com.example.graduationproject.view.Activity.UnWatchActivity;
import com.example.graduationproject.view.Activity.WatchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainFragment extends Fragment implements View.OnClickListener {
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
    private View rootView;
    private Unbinder unbinder;
    private int count;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);

        }
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;


    }

    private void initView() {
        //titleBar = rootView.findViewById(R.id.title_bar);
        titleBar.setTitle("主页");

        getCount();

    }

    @Override
    public void onResume() {
        super.onResume();
        getCount();
    }

    private void getCount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),AppDatabase.class,"myfilm.db").build();
                FilmDao filmDao = db.filmDao();
                count = filmDao.getCount();

                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(() -> {
                    if(count != 0){
                        tvFilmCount.setText(StringUtils.getString(count));
                    }
                });
            }
        }).start();
    }

    @OnClick({R.id.ll_watch, R.id.ll_un_watch, R.id.btn_add})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_watch:
                intent = new Intent(getContext(), WatchActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_un_watch:
                intent = new Intent(getContext(), UnWatchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_add:
                intent = new Intent(getContext(), AddFilmActivity.class);
                startActivity(intent);
                break;
        }
    }
}