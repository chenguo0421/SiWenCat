package cn.com.cg.base.testmvp.view

import android.content.Context
import cn.com.cg.base.BaseActivity
import cn.com.cg.base.testmvp.contract.LoginContract
import cn.com.cg.base.testmvp.presenter.LoginPresenter

class LoginActivity : LoginContract.IView, BaseActivity<LoginContract.IView, LoginContract.IPresenter<LoginContract.IView>>() {

    private lateinit var mPresenter: LoginContract.IPresenter<LoginContract.IView>

    override fun createPresenter(): LoginContract.IPresenter<LoginContract.IView> {
        mPresenter = LoginPresenter()
        return mPresenter
    }

    override fun createView(): LoginContract.IView {
        return this
    }

    override fun getBaseActivity(): Context {
        return this
    }

    override fun initLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}