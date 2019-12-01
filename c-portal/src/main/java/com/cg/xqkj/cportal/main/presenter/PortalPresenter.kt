package com.cg.xqkj.cportal.main.presenter

import com.cg.xqkj.cportal.main.contract.PortalContract
import com.cg.xqkj.cportal.main.model.PortalModel

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:48:43
 *  description : { 请添加该类的描述 }
 */
class PortalPresenter : PortalContract.IPresenter<PortalContract.IView>() {

    private var mModel:PortalModel? = null

    init {
        mModel = PortalModel()
    }
}