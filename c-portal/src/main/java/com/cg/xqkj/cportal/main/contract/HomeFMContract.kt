package com.cg.xqkj.cportal.main.contract

import cn.com.cg.mvp.base.BaseModel
import cn.com.cg.mvp.base.BasePresenter
import cn.com.cg.mvp.base.intf.BaseView

/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:04
 *  description : { 请添加该类的描述 }
 */
class HomeFMContract {

    public interface IView:BaseView{

    }


    public abstract class IPresenter<T> : BasePresenter<BaseView>() {

    }


    public abstract class IModel:BaseModel(){

    }
}