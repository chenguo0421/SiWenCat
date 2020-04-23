package com.cg.xqkj.cportal.splash.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import cn.com.cg.base.BaseActivity

import cn.com.cg.ccommon.utils.Constants
import cn.com.cg.ccommon.utils.SharepreferenceUtils
import cn.com.cg.clog.CLog
import cn.com.cg.router.manager.RouterManager
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.splash.contract.SplashContract
import com.cg.xqkj.cportal.splash.presenter.SplashPresenter
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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

    @SuppressLint("CheckResult")
    private fun gotoPortalActivity() {
        doAsync {


            uiThread {

                RouterManager.getInstance().with(this@SplashActivity).action("PortalActivity").navigation()
                finish()
            }
        }
    }

    override fun initListener() {
    }

}