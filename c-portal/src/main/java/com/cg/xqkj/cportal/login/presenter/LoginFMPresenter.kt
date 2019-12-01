package com.cg.xqkj.cportal.login.presenter

import android.content.Context
import cn.com.cg.cnet.retrofit.base.response.BaseResponse
import com.cg.xqkj.cportal.login.bean.RequestLoginBean
import com.cg.xqkj.cportal.login.bean.ResponseLoginBean
import com.cg.xqkj.cportal.login.contract.LoginFMContract
import com.cg.xqkj.cportal.login.model.LoginFMModel
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:35
 *  description : { 请添加该类的描述 }
 */
class LoginFMPresenter : LoginFMContract.IPresenter<LoginFMContract.IView>() {

    private var mModel:LoginFMModel? = null

    init {
        mModel = LoginFMModel()
    }


    /**
     * 用户名密码登陆
     */
    override fun login(
        context: Context,
        userName: String,
        psw: String,
        bindToLifecycle: LifecycleTransformer<Any>
    ) {
        var params = RequestLoginBean()
        params.userName = userName
        params.psw = psw
        mModel?.login(context,params,bindToLifecycle)
    }

}