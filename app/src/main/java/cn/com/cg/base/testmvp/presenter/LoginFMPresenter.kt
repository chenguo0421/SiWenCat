package cn.com.cg.base.testmvp.presenter

import cn.com.cg.base.testmvp.contract.LoginFMContract
import cn.com.cg.base.testmvp.model.LoginFMModel

class LoginFMPresenter : LoginFMContract.IPresenter<LoginFMContract.IView>() {

    private var mModel:LoginFMModel? = null

    init {
        mModel = LoginFMModel()
    }
}