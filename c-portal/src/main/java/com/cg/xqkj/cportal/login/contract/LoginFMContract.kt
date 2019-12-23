package com.cg.xqkj.cportal.login.contract

import android.content.Context
import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import cn.com.cg.mvp.base.BaseModel
import cn.com.cg.mvp.base.BasePresenter
import cn.com.cg.mvp.base.intf.BaseView
import com.cg.xqkj.cportal.login.bean.RequestLoginBean
import com.cg.xqkj.cportal.login.bean.ResponseLoginBean
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:35
 *  description : { 请添加该类的描述 }
 */
class LoginFMContract {

    public interface IView:BaseView{
        fun onLoginSuccess(data: ResponseLoginBean)
    }


    public abstract class IPresenter<T> : BasePresenter<T>() {
        abstract fun login(
            context: Context,
            userName: String,
            psw: String,
            transformer: LifecycleTransformer<Any>
        )
    }


    public abstract class IModel:BaseModel(){
        abstract fun login(
            params: RequestLoginBean,
            transformer: LifecycleTransformer<Any>,
            observer: ProgressObserver<ResponseLoginBean>
        )

    }
}