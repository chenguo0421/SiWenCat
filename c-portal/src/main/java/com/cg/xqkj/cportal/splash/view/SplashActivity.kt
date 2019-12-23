package com.cg.xqkj.cportal.splash.view

import android.content.Context
import cn.com.cg.base.BaseActivity
import cn.com.cg.ccommon.utils.Constants
import cn.com.cg.ccommon.utils.SharepreferenceUtils
import cn.com.cg.router.manager.RouterManager
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.splash.contract.SplashContract
import com.cg.xqkj.cportal.splash.presenter.SplashPresenter

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:46:59
 *  description : { 导入页 }
 */
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
        return R.layout.portal_activity_splash
    }

    override fun initData() {
        SharepreferenceUtils.put(this, Constants.PortalConstant.IS_LOGIN,false)
        gotoPortalActivity()
    }

    private fun gotoPortalActivity() {
        RouterManager.getInstance().with(this).action("PortalActivity").navigation()
        finish()
    }

    override fun initListener() {
    }

}