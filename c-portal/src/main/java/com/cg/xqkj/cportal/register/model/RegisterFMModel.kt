package com.cg.xqkj.cportal.register.model

import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import cn.com.cg.cnet.retrofit.utils.RetrofitHelper
import com.cg.xqkj.cportal.register.bean.RequestPhoneTokenBean
import com.cg.xqkj.cportal.register.bean.RequestRegisterBean
import com.cg.xqkj.cportal.register.bean.ResponsePhoneTokenBean
import com.cg.xqkj.cportal.register.contract.RegisterFMContract
import com.cg.xqkj.cportal.service.PortalService
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:08
 *  description : { 请添加该类的描述 }
 */
class RegisterFMModel: RegisterFMContract.IModel() {


    open var service: PortalService? = null
    init {
        service = RetrofitHelper.apiSafeService(PortalService::class.java)
    }

    override fun register(
        params: RequestRegisterBean,
        transformer: LifecycleTransformer<Any>,
        observer: ProgressObserver<Any>
    ) {
        service!!.register(params)
            .compose(transformer)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }


    override fun phoneToken(
        params: RequestPhoneTokenBean,
        transformer: LifecycleTransformer<Any>,
        observer: ProgressObserver<ResponsePhoneTokenBean>
    ) {
        service!!.phoneToken(params)
            .compose(transformer)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

}