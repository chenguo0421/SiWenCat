package cn.com.cg.testmodule2.presenter

import cn.com.cg.testmodule2.contract.PortalFMContract
import cn.com.cg.testmodule2.model.PortalFMModel

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/26 9:39
 */
class PortalFMPresenter:PortalFMContract.IPresenter<PortalFMContract.IView>(){
    private var mModel: PortalFMModel? = null

    init {
        mModel = PortalFMModel()
    }
}