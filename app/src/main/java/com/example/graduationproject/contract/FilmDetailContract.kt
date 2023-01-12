package com.example.graduationproject.contract

import com.example.graduationproject.model.FilmBean
import com.example.graduationproject.model.FilmDetailBean
import mvp.ljb.kt.contract.IModelContract
import mvp.ljb.kt.contract.IPresenterContract
import mvp.ljb.kt.contract.IViewContract

/**
 * @Author Kotlin MVP Plugin
 * @Date 2022/12/02
 * @Description input description
 **/
interface FilmDetailContract {

    interface IPresenter : IPresenterContract{
        /**
         * 获取电影列表
         */
        // TODO:  params: ServiceParams? 请求参数
        fun requestFilmListResult(url: String?)

    }

    interface IView : IViewContract {
        /**
         * 处理电影详情
         */
        fun handleFilmDetailResult(result: FilmDetailBean)

    }


    interface IModel : IModelContract

}
