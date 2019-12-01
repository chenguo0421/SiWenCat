package com.cg.xqkj.cportal.splash.presenter

import com.cg.xqkj.cportal.splash.contract.SplashContract
import com.cg.xqkj.cportal.splash.model.SplashModel

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:46:59
 *  description : { 请添加该类的描述 }
 */
class SplashPresenter : SplashContract.IPresenter<SplashContract.IView>() {

    private var mModel:SplashModel? = null

    init {
        mModel = SplashModel()
    }
}