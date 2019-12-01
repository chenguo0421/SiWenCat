package com.cg.xqkj.cportal.main.presenter

import com.cg.xqkj.cportal.main.contract.GiftFMContract
import com.cg.xqkj.cportal.main.model.GiftFMModel

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:55:15
 *  description : { 请添加该类的描述 }
 */
class GiftFMPresenter : GiftFMContract.IPresenter<GiftFMContract.IView>() {

    private var mModel:GiftFMModel? = null

    init {
        mModel = GiftFMModel()
    }
}