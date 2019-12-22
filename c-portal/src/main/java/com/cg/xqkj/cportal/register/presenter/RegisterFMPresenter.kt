package com.cg.xqkj.cportal.register.presenter

import android.content.Context
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.register.bean.RequestPhoneTokenBean
import com.cg.xqkj.cportal.register.bean.RequestRegisterBean
import com.cg.xqkj.cportal.register.bean.ResponsePhoneTokenBean
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
        params.imei = ""
        mModel?.register(params,transformer,object :  ProgressObserver<Any>(context){
            override fun success(data: Any) {
                getView()?.onPhoneNumSubOK(data)
            }

            override fun failure(code: Int, msg: String) {
                ToastUtils.show(String.format(getView()?.getBaseActivity()!!.getString(R.string.portal_register_phone_submit_error),msg))
            }
        })
    }

    override fun getPhoneToken(context: Context, phone: String, transformer: LifecycleTransformer<Any>) {
        var params = RequestPhoneTokenBean()
        params.phone = phone
        params.imei = ""
        mModel?.phoneToken(params,transformer,object :  ProgressObserver<ResponsePhoneTokenBean>(context){
            override fun success(data: ResponsePhoneTokenBean) {
                getView()?.onPhoneTokenOK(data)
            }

            override fun failure(code: Int, msg: String) {
                ToastUtils.show(String.format(getView()?.getBaseActivity()!!.getString(R.string.portal_register_phone_submit_error),msg))
            }
        })
    }

}