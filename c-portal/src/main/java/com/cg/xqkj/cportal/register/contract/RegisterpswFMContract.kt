package com.cg.xqkj.cportal.register.contract

import android.content.Context
import cn.com.cg.cnet.retrofit.observelistener.ProgressObserver
import cn.com.cg.mvp.base.BaseModel
import cn.com.cg.mvp.base.BasePresenter
import cn.com.cg.mvp.base.intf.BaseView
import com.cg.xqkj.cportal.register.bean.RequestRegisterPSWBean
import com.cg.xqkj.cportal.register.bean.ResponseRegisterPSWBean
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 *  author : ChenGuo
 *  date : 2019-12-09 21:20:34
 *  description : { 请添加该类的描述 }
 */
class RegisterpswFMContract {

    public interface IView:BaseView{

    }


    public abstract class IPresenter<T> : BasePresenter<T>() {
        abstract fun submitPSW(
            context: Context,
            psw: String,
            confirmPsw: String,
            transformer: LifecycleTransformer<Any>
        )
    }


    public abstract class IModel:BaseModel(){
        abstract fun submitPSW(
            params: RequestRegisterPSWBean,
            transformer: LifecycleTransformer<Any>,
            observer: ProgressObserver<ResponseRegisterPSWBean>
        )
    }
}