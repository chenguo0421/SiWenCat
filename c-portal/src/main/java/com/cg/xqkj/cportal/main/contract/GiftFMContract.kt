package com.cg.xqkj.cportal.main.contract

import cn.com.cg.mvp.base.BaseModel
import cn.com.cg.mvp.base.BasePresenter
import cn.com.cg.mvp.base.intf.BaseView
import com.cg.xqkj.cportal.main.bean.GiftDJYPBean

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:55:15
 *  description : { 请添加该类的描述 }
 */
class GiftFMContract {

    public interface IView:BaseView{
        abstract fun onLoadGiftDJYPDataSuccess(list: GiftDJYPBean?)

    }


    public abstract class IPresenter<T> : BasePresenter<T>() {
        abstract fun getGiftDJYPData()

    }


    public abstract class IModel:BaseModel(){

    }
}