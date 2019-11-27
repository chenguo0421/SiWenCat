package cn.com.cg.base.testmvp.view.fragment

import android.content.Context
import cn.com.cg.base.BaseFragment
import cn.com.cg.base.testmvp.contract.LoginFMContract
import cn.com.cg.base.testmvp.presenter.LoginFMPresenter

class LoginFragment :LoginFMContract.IView, BaseFragment<LoginFMContract.IView, LoginFMContract.IPresenter<LoginFMContract.IView>>() {

    private lateinit var mPresenter: LoginFMContract.IPresenter<LoginFMContract.IView>

    override fun createPresenter(): LoginFMContract.IPresenter<LoginFMContract.IView> {
        mPresenter = LoginFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseFragment<LoginFMContract.IView, LoginFMContract.IPresenter<LoginFMContract.IView>> {
        synchronized(LoginFragment::class){
            return LoginFragment()
        }
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun createView(): LoginFMContract.IView {
        return this
    }

    override fun initListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}