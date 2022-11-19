package com.example.graduationproject.view.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.graduationproject.R;
import com.example.graduationproject.adapter.MyFragmentPagerAdapter;
import com.example.graduationproject.view.fragment.CollectionFragment;
import com.example.graduationproject.view.fragment.MainFragment;
import com.example.graduationproject.view.fragment.MineFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.img_home)
    ImageView img_home;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    @BindView(R.id.img_collect)
    ImageView img_collect;
    @BindView(R.id.ll_collect)
    LinearLayout ll_collect;
    @BindView(R.id.img_mine)
    ImageView img_mine;
    @BindView(R.id.ll_mine)
    LinearLayout ll_mine;


    private ArrayList<Fragment> fragments;
    private MyFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initPager();

        // 设置菜单栏的点击事件
        ll_main.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new MyPagerChangeListener());
    }


    private void initView() {
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        ll_collect = (LinearLayout) findViewById(R.id.ll_collect);
        ll_mine = (LinearLayout) findViewById(R.id.ll_mine);
        img_home = (ImageView) findViewById(R.id.img_home);
        img_collect = (ImageView) findViewById(R.id.img_collect);
        img_mine = (ImageView) findViewById(R.id.img_mine);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    //适配fragment
    private void initPager() {
        viewPager = findViewById(R.id.viewpager);
        fragments = new ArrayList<Fragment>();

        fragments.add(new MainFragment());
        fragments.add(new CollectionFragment());
        fragments.add(new MineFragment());

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
    }


    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_main:
                viewPager.setCurrentItem(0);
                img_home.setImageResource(R.mipmap.icon_home_on);
                img_collect.setImageResource(R.mipmap.icon_collection_off);
                img_mine.setImageResource(R.mipmap.icon_mine_off);
                break;
            case R.id.ll_collect:
                viewPager.setCurrentItem(1);
                img_home.setImageResource(R.mipmap.icon_home_off);
                img_collect.setImageResource(R.mipmap.icon_collection_on);
                img_mine.setImageResource(R.mipmap.icon_mine_off);
                break;
            case R.id.ll_mine:
                viewPager.setCurrentItem(2);
                img_home.setImageResource(R.mipmap.icon_home_off);
                img_collect.setImageResource(R.mipmap.icon_collection_off);
                img_mine.setImageResource(R.mipmap.icon_mine_on);
                break;
            default:
                break;
        }
    }

    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     */
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    img_home.setImageResource(R.mipmap.icon_home_on);
                    img_collect.setImageResource(R.mipmap.icon_collection_off);
                    img_mine.setImageResource(R.mipmap.icon_mine_off);
                    break;
                case 1:
                    img_home.setImageResource(R.mipmap.icon_home_off);
                    img_collect.setImageResource(R.mipmap.icon_collection_on);
                    img_mine.setImageResource(R.mipmap.icon_mine_off);
                    break;
                case 2:
                    img_home.setImageResource(R.mipmap.icon_home_off);
                    img_collect.setImageResource(R.mipmap.icon_collection_off);
                    img_mine.setImageResource(R.mipmap.icon_mine_on);
                    break;
                default:
                    break;
            }

        }
    }

}