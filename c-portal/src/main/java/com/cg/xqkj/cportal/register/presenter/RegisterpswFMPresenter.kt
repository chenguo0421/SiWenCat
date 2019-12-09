package com.cg.xqkj.cportal.register.presenter

import android.content.Context
import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import com.cg.xqkj.cportal.register.bean.RequestRegisterPSWBean
import com.cg.xqkj.cportal.register.bean.ResponseRegisterPSWBean
import com.cg.xqkj.cportal.register.contract.RegisterpswFMContract
import com.cg.xqkj.cportal.register.model.RegisterpswFMModel
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 *  author : ChenGuo
 *  date : 2019-12-09 21:20:34
 *  description : { 请添加该类的描述 }
 */
class RegisterpswFMPresenter : RegisterpswFMContract.IPresenter<RegisterpswFMContract.IView>() {

    private var mModel:RegisterpswFMModel? = null

    init {
        mModel = RegisterpswFMModel()
    }

    override fun submitPSW(context: Context, psw: String, confirmPsw: String, transformer: LifecycleTransformer<Any>) {
        var params = RequestRegisterPSWBean()
        params.psw = psw
        params.confirmPsw = confirmPsw
        mModel?.submitPSW(params, transformer, object : ProgressObserver<ResponseRegisterPSWBean>(context) {
            override fun success(data: ResponseRegisterPSWBean) {
            }

            override fun failure(code: Int, msg: String) {
            }

        })
    }

}