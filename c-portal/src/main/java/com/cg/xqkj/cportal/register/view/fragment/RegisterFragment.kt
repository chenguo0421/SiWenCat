package com.cg.xqkj.cportal.register.view.fragment

import android.content.Context
import cn.com.cg.base.BaseDialogFragment
import com.cg.xqkj.cportal.register.contract.RegisterFMContract
import com.cg.xqkj.cportal.register.presenter.RegisterFMPresenter

/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:08
 *  description : { 请添加该类的描述 }
 */
class RegisterFragment :RegisterFMContract.IView, BaseDialogFragment<RegisterFMContract.IView, RegisterFMContract.IPresenter<RegisterFMContract.IView>>() {

    private lateinit var mPresenter: RegisterFMContract.IPresenter<RegisterFMContract.IView>

    override fun createPresenter(): RegisterFMContract.IPresenter<RegisterFMContract.IView> {
        mPresenter = RegisterFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseDialogFragment<RegisterFMContract.IView, RegisterFMContract.IPresenter<RegisterFMContract.IView>> {
        synchronized(RegisterFragment::class){
            return RegisterFragment()
        }
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun createView(): RegisterFMContract.IView {
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