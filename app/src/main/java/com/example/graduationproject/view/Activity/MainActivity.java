package com.example.graduationproject.view.Activity;

import android.annotation.SuppressLint;
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
@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity{

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.img_home)
    ImageView imgHome;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.img_collect)
    ImageView imgCollect;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.img_mine)
    ImageView imgMine;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;

    private ArrayList<Fragment> fragments;
    private MyFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        initView();
        initPager();

        // 设置菜单栏的点击事件
        llMain.setOnClickListener(v -> viewPager.setCurrentItem(0));
        llCollect.setOnClickListener(v -> viewPager.setCurrentItem(1));
        llMine.setOnClickListener(v -> viewPager.setCurrentItem(2));
        viewPager.setOnPageChangeListener(new MyPagerChangeListener());
    }

    //适配fragment
    private void initPager() {
        viewPager = findViewById(R.id.viewpager);
        fragments = new ArrayList<>();

        fragments.add(new MainFragment());
        fragments.add(new CollectionFragment());
        fragments.add(new MineFragment());

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
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
                    imgHome.setImageResource(R.mipmap.icon_home_on);
                    imgCollect.setImageResource(R.mipmap.icon_collection_off);
                    imgMine.setImageResource(R.mipmap.icon_mine_off);
                    break;
                case 1:
                    imgHome.setImageResource(R.mipmap.icon_home_off);
                    imgCollect.setImageResource(R.mipmap.icon_collection_on);
                    imgMine.setImageResource(R.mipmap.icon_mine_off);
                    break;
                case 2:
                    imgHome.setImageResource(R.mipmap.icon_home_off);
                    imgCollect.setImageResource(R.mipmap.icon_collection_off);
                    imgMine.setImageResource(R.mipmap.icon_mine_on);
                    break;
                default:
                    break;
            }
        }
    }
}