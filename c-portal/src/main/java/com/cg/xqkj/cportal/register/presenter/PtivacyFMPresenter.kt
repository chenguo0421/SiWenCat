package com.cg.xqkj.cportal.register.presenter

import com.cg.xqkj.cportal.register.contract.PtivacyFMContract
import com.cg.xqkj.cportal.register.model.PtivacyFMModel

/**
 *  author : ChenGuo
 *  date : 2019-12-06 19:54:48
 *  description : { 请添加该类的描述 }
 */
class PtivacyFMPresenter : PtivacyFMContract.IPresenter<PtivacyFMContract.IView>() {

    private var mModel:PtivacyFMModel? = null

    init {
        mModel = PtivacyFMModel()
    }
}