package com.cg.xqkj.cportal.register.model

import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import cn.com.cg.cnet.retrofit.utils.RetrofitHelper
import com.cg.xqkj.cportal.register.bean.RequestRegisterPSWBean
import com.cg.xqkj.cportal.register.bean.ResponseRegisterPSWBean
import com.cg.xqkj.cportal.register.contract.RegisterpswFMContract
import com.cg.xqkj.cportal.service.PortalService
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *  author : ChenGuo
 *  date : 2019-12-09 21:20:34
 *  description : { 请添加该类的描述 }
 */
class RegisterpswFMModel: RegisterpswFMContract.IModel() {

    open var service: PortalService? = null
    init {
        service = RetrofitHelper.apiSafeService(PortalService::class.java)
    }

    override fun submitPSW(
        params: RequestRegisterPSWBean,
        transformer: LifecycleTransformer<Any>,
        observer: ProgressObserver<ResponseRegisterPSWBean>
    ) {
        service!!.submitPSW(params)
            .compose(transformer)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

}