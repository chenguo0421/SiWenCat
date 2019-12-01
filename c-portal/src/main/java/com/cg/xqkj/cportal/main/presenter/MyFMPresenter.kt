package com.cg.xqkj.cportal.main.presenter

import com.cg.xqkj.cportal.main.contract.MyFMContract
import com.cg.xqkj.cportal.main.model.MyFMModel

/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:52
 *  description : { 请添加该类的描述 }
 */
class MyFMPresenter : MyFMContract.IPresenter<MyFMContract.IView>() {

    private var mModel:MyFMModel? = null

    init {
        mModel = MyFMModel()
    }
}