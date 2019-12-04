package com.cg.xqkj.cportal.login.model

import android.content.Context
import cn.com.cg.clog.CLog
import cn.com.cg.cnet.retrofit.base.response.BaseResponse
import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import cn.com.cg.cnet.retrofit.utils.RetrofitHelper
import com.cg.xqkj.cportal.login.bean.RequestLoginBean
import com.cg.xqkj.cportal.login.bean.ResponseLoginBean
import com.cg.xqkj.cportal.login.contract.LoginFMContract
import com.cg.xqkj.cportal.service.PortalService
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:35
 *  description : { 请添加该类的描述 }
 */
class LoginFMModel: LoginFMContract.IModel() {

    open var service:PortalService? = null
    init {
        service = RetrofitHelper.apiSafeService(PortalService::class.java)
    }


    override fun login(
        context: Context,
        params: RequestLoginBean,
        transformer: LifecycleTransformer<Any>,
        observer: ProgressObserver<ResponseLoginBean>
    ) {
        service!!.login(params)
            .compose(transformer)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    }


}