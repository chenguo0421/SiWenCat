package com.cg.xqkj.cportal.register.contract

import android.content.Context
import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import cn.com.cg.mvp.base.BaseModel
import cn.com.cg.mvp.base.BasePresenter
import cn.com.cg.mvp.base.intf.BaseView
import com.cg.xqkj.cportal.register.bean.RequestRegisterBean
import com.cg.xqkj.cportal.register.bean.ResponseRegisterBean
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:08
 *  description : { 请添加该类的描述 }
 */
class RegisterFMContract {

    public interface IView:BaseView{

    }


    public abstract class IPresenter<T> : BasePresenter<T>() {
        abstract fun register(
            context: Context,
            phone: String,
            authCode: String,
            transformer: LifecycleTransformer<Any>
        )
    }


    public abstract class IModel:BaseModel(){
        abstract fun register(
            params: RequestRegisterBean,
            transformer: LifecycleTransformer<Any>,
            observer: ProgressObserver<ResponseRegisterBean>
        )
    }
}