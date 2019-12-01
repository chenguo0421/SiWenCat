package cn.com.cg.mvp.base

import android.content.Context
import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/25 15:05
 */
open class BaseModel {

    fun subscribe(
        observable: Observable<*>,
        observer:  ProgressObserver<Any>,
        transformer: ObservableTransformer<Any,Any>
    ) {
        observable.compose(transformer)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}


