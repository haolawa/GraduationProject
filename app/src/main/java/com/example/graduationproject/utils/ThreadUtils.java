package com.example.graduationproject.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import com.example.graduationproject.adapter.FilmSaveAdapter;
import com.example.graduationproject.controller.AppDatabase;
import com.example.graduationproject.controller.FilmDao;
import com.example.graduationproject.model.FilmSaveBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author luowen
 * @date 2022/11/19
 * des
 **/
public class ThreadUtils {
    public static final int isWatch = 0;
    public static final int isUnWatch = 1;
    public static final int isLove = 2;

    public static void filmList(Context context, List<FilmSaveBean> beans, FilmSaveAdapter adapter, int tag) {
        /**
         * corePoolSize线程池的核心线程数
         * maximumPoolSize能容纳的最大线程数
         * keepAliveTime空闲线程存活时间
         * unit 存活的时间单位
         * workQueue 存放提交但未执行任务的队列
         * threadFactory 创建线程的工厂类
         * handler 等待队列满后的拒绝策略
         */
        ExecutorService threadPool = new ThreadPoolExecutor(2, 5,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        threadPool.execute(() -> {
            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "myfilm.db")
                    .build();
            FilmDao filmDao = db.filmDao();
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
            mainHandler.post(() -> {
                adapter.notifyDataSetChanged();
            });
        });
    }
}
