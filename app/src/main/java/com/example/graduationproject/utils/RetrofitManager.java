package com.example.graduationproject.utils;

import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @created by xh on 2020/2/27 20:47
 * @email:
 * @desc: Retrofit单例
 */
public class RetrofitManager {

    /**
     * 连接超时时间
     */
    private static final int DEFAULT_CONNECT_TIME = 10;
    /**
     * 写操作超时时间
     */
    private static final int DEFAULT_WRITE_TIME = 60;
    /**
     * 读操作超时时间
     */
    private static final int DEFAULT_READ_TIME = 60;

    private final OkHttpClient okHttpClient;
    private final Retrofit retrofit;
    private ProcessCallback processCallback;//下载进度回调
    private UploadProcessCallback uploadProcessCallback;//上传进度回调

    private RetrofitManager() {
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addNetworkInterceptor(new HttpLoggingInterceptor(Logger::i).setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new HttpLoggingInterceptor())
                .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.IMDB_BASE_URL)//设置服务器路径
                .callFactory(new CallFactoryProxy((Call.Factory) okHttpClient) {
                    @Override
                    protected HttpUrl getNewUrl(String baseUrlName, Request request) {
                        if (!TextUtils.isEmpty(baseUrlName)) {
//                            String oldUrl = request.url().toString();
//                            String newUrl = oldUrl.replace(Constants.IMDB_BASE_URL, baseUrlName);//替换老的地址
                            String newUrl = baseUrlName;//替换老的地址
                            return HttpUrl.get(newUrl);
                        }
                        return request.url();
                    }
                })
                .addConverterFactory(GsonConverterFactory.create())//添加转化库
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加回调库，采用RxJava
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())//添加回调库，采用RxJava
                .build();

    }

    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setProcessCallback(ProcessCallback processCallback) {
        this.processCallback = processCallback;
    }

    public void setUploadProcessCallback(UploadProcessCallback uploadProcessCallback) {
        this.uploadProcessCallback = uploadProcessCallback;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    public interface ProcessCallback {
        void process(long currentSize, long totalSize, boolean isComplete);
    }

    public interface UploadProcessCallback {
        void process(long currentSize, long totalSize, boolean isComplete);
    }
}
