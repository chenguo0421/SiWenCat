package com.cg.xqkj.cportal.register.presenter

import android.content.Context
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import com.cg.xqkj.cportal.R
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

    override fun submitPSW(context: Context,phone:String,randomKey:String, psw: String, confirmPsw: String, transformer: LifecycleTransformer<Any>) {
        val params = RequestRegisterPSWBean()
        params.psw = psw
        params.phone = phone
        params.randomKey = randomKey
        params.confirmPsw = confirmPsw
        params.phone = phone
        params.imei = ""
        mModel?.submitPSW(params, transformer, object : ProgressObserver<ResponseRegisterPSWBean>(context) {
            override fun success(data: ResponseRegisterPSWBean) {
                getView()?.onRegisterSuccess(data)
            }

            override fun failure(code: Int, msg: String) {
                ToastUtils.show(String.format(getView()?.getBaseActivity()!!.resources.getString(R.string.portal_register_psw_submit_error),msg))
            }

        })
    }

}