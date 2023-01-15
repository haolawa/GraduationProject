package com.example.graduationproject.presenter

import android.util.Log
import com.example.graduationproject.contract.FilmService
import com.example.graduationproject.contract.FilmDetailContract
import com.example.graduationproject.model.FilmDetailBean
import com.example.graduationproject.model.WebModel
import com.example.graduationproject.utils.RetrofitManager
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mvp.ljb.kt.presenter.BaseMvpPresenter

/**
 * @Author luowen
 * @Date 2022/12/02
 * @Description input description
 **/
open class FilmDetailPresenter(var view: FilmDetailContract.IView?) :
    BaseMvpPresenter<FilmDetailContract.IView, FilmDetailContract.IModel>(), FilmDetailContract.IPresenter {

    override fun registerModel() = WebModel::class.java

    override fun requestFilmDetailResult(url: String?) {
        // TODO: Retrofit + RxJava(lambda)使用
        RetrofitManager.getInstance().create(FilmService::class.java)
            .getFilmRequest(url)
            .subscribeOn(Schedulers.io())//给上游分配异步线程
            .observeOn(AndroidSchedulers.mainThread())//给下游分配主线程
            .subscribe(object : Observer<FilmDetailBean> {
                override fun onSubscribe(disposable: Disposable) {

                }

                override fun onNext(filmbean: FilmDetailBean) {
                    view?.let { register(it) }
                    getMvpView().handleFilmDetailResult(filmbean)
                }

                override fun onError(throwable: Throwable) {
                    Log.e("haolawa", throwable.toString())
                }

                override fun onComplete() {
                }
            })
    }

}
