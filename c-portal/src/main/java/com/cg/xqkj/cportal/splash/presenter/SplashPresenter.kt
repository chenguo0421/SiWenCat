package com.cg.xqkj.cportal.splash.presenter

import com.cg.xqkj.cportal.splash.contract.SplashContract
import com.cg.xqkj.cportal.splash.model.SplashModel

class SplashPresenter : SplashContract.IPresenter<SplashContract.IView>() {

    private var mModel:SplashModel? = null

    init {
        mModel = SplashModel()
    }
}