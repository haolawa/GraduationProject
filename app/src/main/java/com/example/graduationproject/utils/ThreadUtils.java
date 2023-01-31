package com.example.graduationproject.utils;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.room.Room;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.graduationproject.adapter.FilmSaveAdapter;
import com.example.graduationproject.controller.AppDatabase;
import com.example.graduationproject.controller.FilmDao;
import com.example.graduationproject.controller.SPUtils;
import com.example.graduationproject.controller.UserDao;
import com.example.graduationproject.model.FilmSaveBean;
import com.example.graduationproject.model.UserData;
import com.example.graduationproject.view.Activity.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author luowen
 * @date 2022/11/19
 * des
 **/
public class ThreadUtils {
    public static final int isWatch = 0;
    public static final int isUnWatch = 1;
    public static final int isLove = 2;
    private static final String DBNAME = "myfilm.db";
    /**
     * corePoolSize线程池的核心线程数
     * maximumPoolSize能容纳的最大线程数
     * keepAliveTime空闲线程存活时间
     * unit 存活的时间单位
     * workQueue 存放提交但未执行任务的队列
     * threadFactory 创建线程的工厂类
     * handler 等待队列满后的拒绝策略
     */
    private static final ExecutorService threadPool = new ThreadPoolExecutor(2, 5,
            1L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());


    public static void filmList(Context context, List<FilmSaveBean> beans, FilmSaveAdapter adapter, int tag) {
        FilmDao filmDao = getFilmDao(context);
        threadPool.execute(() -> {
            List<FilmSaveBean> filmList = new ArrayList<>();
            switch (tag) {
                case 0:
                    filmList = filmDao.getWatch(true);
                    break;
                case 1:
                    filmList = filmDao.getWatch(false);
                    break;
                case 2:
                    filmList = filmDao.getLove(true);
                    break;
                default:
                    break;
            }

            beans.clear();
            beans.addAll(filmList);
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(adapter::notifyDataSetChanged);
        });
    }

    public static void filmCount(Context context, TextView textView) {
        AtomicInteger count = new AtomicInteger();
        FilmDao filmDao = getFilmDao(context);
        threadPool.execute(() -> {
            count.set(filmDao.getCount());
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(() -> {
                if (count.intValue() != 0) {
                    textView.setText(StringUtils.getString(count.intValue()));
                }
            });
        });
    }

    public static void filmSearch(Context context, String search, List<FilmSaveBean> beans, FilmSaveAdapter adapter) {
        FilmDao filmDao = getFilmDao(context);
        threadPool.execute(() -> {
            List<FilmSaveBean> filmList = filmDao.getNameFilm(search);
            beans.clear();
            beans.addAll(filmList);
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(adapter::notifyDataSetChanged);
        });
    }

    public static void filmSave(Context context, FilmSaveBean filmBean) {
        FilmDao filmDao = getFilmDao(context);
        threadPool.execute(() -> {
            filmDao.upData(filmBean);
        });
    }

    public static void insertFilmData(Context context, FilmSaveBean filmBean) {
        FilmDao filmDao = getFilmDao(context);
        threadPool.execute(() -> {
            filmDao.insertData(filmBean);
        });
    }

    public static void filmDelete(Context context, FilmSaveBean filmBean) {
        FilmDao filmDao = getFilmDao(context);
        threadPool.execute(() -> {
            filmDao.delete(filmBean);
        });
    }

    private static FilmDao getFilmDao(Context context) {
        //AppDatabase db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DBNAME).build();
        //添加一个addMigration或者调用fallbackToDestructiveMigration完成迁移
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .filmDao();
    }

    private static UserDao getUserDao(Context context) {
        //AppDatabase db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DBNAME).build();
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .userDao();
    }

    /*
     * 用户数据
     */
    public static void insertUserData(Context context, UserData userData) {
        UserDao userDao = getUserDao(context);
        threadPool.execute(() -> {
            userDao.insertData(userData);
        });
    }

    public static void getUserData(Context context, String phone,String password) {
        UserDao userDao = getUserDao(context);
        threadPool.execute(() -> {
            UserData userData = userDao.getUserData(phone);
            if(null == userData){
                ToastUtils.showShort("该手机号未注册");
                return;
            }
            if(!userData.getPassword().equals(password)){
                ToastUtils.showShort("密码错误");
                return;
            }
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });
    }
}
