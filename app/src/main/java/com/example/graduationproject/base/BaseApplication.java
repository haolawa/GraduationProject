package com.example.graduationproject.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {

    private static Context mApplicationContext;
    private List<Activity> activityList = new LinkedList<>();
    private static final String TAG = "9999";

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationContext = this;

        Log.e(TAG, "BaseApplication onCreate" );
        //initOkGo();

        int pid = android.os.Process.myPid();
        Log.e(TAG, "onCreate: pid: " + pid );

        inFresco();
        //JPushInterface.setDebugMode(true);
        //JPushInterface.init(this);
    }

    private void inFresco() {

        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(mApplicationContext)
                .setMaxCacheSize(1080 * 1080 * 500)
                .setBaseDirectoryPath(new File(Environment.getDataDirectory().getParent() + "/wine_image"))
                .build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(mApplicationContext)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();

        Fresco.initialize(mApplicationContext,imagePipelineConfig);

    }


//    private void initOkGo() {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        //log相关
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
//        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
//        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
//        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
//        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
//        //builder.addInterceptor(new ChuckInterceptor(this));
//
//        //超时时间设置，默认60秒
//        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
//        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
//        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//
//
//        // 其他统一的配置
//        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
//        OkGo.getInstance().init(this)                           //必须调用初始化
//                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
////                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
////                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
//                .setRetryCount(3);                         //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
////                .addCommonHeaders(headers)                      //全局公共头
////                .addCommonParams(params);                       //全局公共参数
//    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public void exit() {
        exitApp();
        System.exit(0);
    }

    public void exitApp() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    // 获取ApplicationContext

    public static Context getContext() {
        return mApplicationContext;
    }
}
