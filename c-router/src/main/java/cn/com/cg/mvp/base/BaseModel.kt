package cn.com.cg.mvp.base

import android.content.Context
import cn.com.cg.cnet.retrofit.base.BaseApi
import cn.com.cg.cnet.retrofit.base.intf.BaseService
import cn.com.cg.cnet.retrofit.observelistener.ObserveResponseListener
import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import cn.com.cg.cnet.retrofit.utils.RetrofitHelper

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/25 15:05
 */
abstract class BaseModel {

    open var service:BaseService? = null
    init {
        service = RetrofitHelper.apiSafeService(BaseService::class.java)
    }


    fun subscribe(
        context: Context,
        observable: Observable<*>,
        listener: ObserveResponseListener,
        transformer: ObservableTransformer<Any,Any>
    ) {
        val observer = ProgressObserver(context, listener)
        observable.compose(transformer)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}