package cn.com.cg.base.testmvp.presenter

import cn.com.cg.base.testmvp.contract.LoginContract
import cn.com.cg.base.testmvp.model.LoginModel

class LoginPresenter : LoginContract.IPresenter<LoginContract.IView>() {

    private var mModel:LoginModel? = null

    init {
        mModel = LoginModel()
    }
}