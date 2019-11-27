package com.cg.xqkj.cportal.splash.view

import android.content.Context
import android.widget.TextView
import cn.com.cg.base.BaseActivity
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.splash.contract.SplashContract
import com.cg.xqkj.cportal.splash.presenter.SplashPresenter
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : SplashContract.IView, BaseActivity<SplashContract.IView, SplashContract.IPresenter<SplashContract.IView>>() {

    private lateinit var mPresenter: SplashContract.IPresenter<SplashContract.IView>


    override fun createPresenter(): SplashContract.IPresenter<SplashContract.IView> {
        mPresenter = SplashPresenter()
        return mPresenter
    }

    override fun createView(): SplashContract.IView {
        return this
    }

    override fun getBaseActivity(): Context {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.splash_activity
    }

    override fun initData() {
        splash_tv?.text = "test kotlin"
    }

    override fun initListener() {
    }

}