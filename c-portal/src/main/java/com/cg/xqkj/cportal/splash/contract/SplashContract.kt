package com.cg.xqkj.cportal.splash.contract

import cn.com.cg.mvp.base.BaseModel
import cn.com.cg.mvp.base.BasePresenter
import cn.com.cg.mvp.base.intf.BaseView

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:46:59
 *  description : { 请添加该类的描述 }
 */
class SplashContract {

    public interface IView:BaseView{

    }


    public abstract class IPresenter<T> : BasePresenter<BaseView>() {

    }


    public abstract class IModel:BaseModel(){

    }
}