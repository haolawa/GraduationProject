package com.example.graduationproject.contract;

import static com.example.graduationproject.utils.CallFactoryProxy.NAME_BASE_URL;

import com.example.graduationproject.model.FilmDetailBean;

import io.reactivex.Observable;
import okhttp3.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * @author luowen
 * @since 2022/3/4
 * for: 请求接口
 */
public interface FilmService {

    /**
     * 请求电影地址
     */
    @GET("/")
    Observable<FilmDetailBean> getFilmRequest(@Header(NAME_BASE_URL) String url);

    /**
     * 请求电影列表
     */
    @GET("/")
    Observable<Response> getFilmListRequest(@Header(NAME_BASE_URL) String url);
}
