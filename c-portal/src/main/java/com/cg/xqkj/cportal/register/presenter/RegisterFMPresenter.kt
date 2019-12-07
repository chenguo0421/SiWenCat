package com.cg.xqkj.cportal.register.presenter

import android.content.Context
import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import com.cg.xqkj.cportal.register.bean.RequestRegisterBean
import com.cg.xqkj.cportal.register.bean.ResponseRegisterBean
import com.cg.xqkj.cportal.register.contract.RegisterFMContract
import com.cg.xqkj.cportal.register.model.RegisterFMModel
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:08
 *  description : { 请添加该类的描述 }
 */
class RegisterFMPresenter : RegisterFMContract.IPresenter<RegisterFMContract.IView>() {

    private var mModel:RegisterFMModel? = null

    init {
        mModel = RegisterFMModel()
    }

    override fun register(
        context: Context,
        phone: String,
        authCode: String,
        transformer: LifecycleTransformer<Any>
    ) {
        var params = RequestRegisterBean()
        params.phone = phone
        params.authCode = authCode
        mModel?.register(params,transformer,object :  ProgressObserver<ResponseRegisterBean>(context){
            override fun success(data: ResponseRegisterBean) {

            }

            override fun failure(code: Int, msg: String) {

            }

        })
    }
}