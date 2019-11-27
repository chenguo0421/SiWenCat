package cn.com.cg.testmodule2.presenter

import cn.com.cg.testmodule2.contract.MyFMContract
import cn.com.cg.testmodule2.model.MyFMModel

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/26 9:39
 */
class MyFMPresenter:MyFMContract.IPresenter<MyFMContract.IView>(){
    private var mModel:MyFMModel? = null

    init {
        mModel = MyFMModel()
    }
}