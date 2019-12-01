package com.cg.xqkj.cportal.register.presenter

import com.cg.xqkj.cportal.register.contract.RegisterFMContract
import com.cg.xqkj.cportal.register.model.RegisterFMModel

/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:08
 *  description : { 请添加该类的描述 }
 */
class RegisterFMPresenter : RegisterFMContract.IPresenter<RegisterFMContract.IView>() {

    private var mModel:RegisterFMModel? = null

    init {
        mModel = RegisterFMModel()
    }
}