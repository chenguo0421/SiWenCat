package cn.com.cg.testmodule1.contract

import cn.com.cg.mvp.base.BaseModel
import cn.com.cg.mvp.base.BasePresenter
import cn.com.cg.mvp.base.intf.BaseView

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/26 9:28
 */
abstract class TestFMContract {
    public interface IView: BaseView {

    }


    public abstract class IPresenter<T> : BasePresenter<T>() {

    }


    public abstract class IModel: BaseModel(){

    }
}