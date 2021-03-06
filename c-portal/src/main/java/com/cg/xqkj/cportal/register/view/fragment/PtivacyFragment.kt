package com.cg.xqkj.cportal.register.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import cn.com.cg.base.BaseDialogFragment
import cn.com.cg.ccommon.utils.DeviceUtils
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.register.contract.PtivacyFMContract
import com.cg.xqkj.cportal.register.presenter.PtivacyFMPresenter
import kotlinx.android.synthetic.main.portal_fragment_privacy.*

/**
 *  author : ChenGuo
 *  date : 2019-12-06 19:54:48
 *  description : { 隐私协议页面 }
 */
@CRouter(path = "PtivacyFragment")
class PtivacyFragment :PtivacyFMContract.IView, BaseDialogFragment<PtivacyFMContract.IView, PtivacyFMContract.IPresenter<PtivacyFMContract.IView>>() {
    private lateinit var bundle:Bundle
    private lateinit var mPresenter: PtivacyFMContract.IPresenter<PtivacyFMContract.IView>

    override fun createPresenter(): PtivacyFMContract.IPresenter<PtivacyFMContract.IView> {
        mPresenter = PtivacyFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseDialogFragment<PtivacyFMContract.IView, PtivacyFMContract.IPresenter<PtivacyFMContract.IView>> {
        synchronized(PtivacyFragment::class){
            return PtivacyFragment()
        }
    }

    override fun fragmentIOAnimation(): Int {
        return R.style.BottomAnimation
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun setBundleExtra(bundle: Bundle) {
        this.bundle = bundle
    }

    override fun setDialogWidth(): Int {
        return DeviceUtils.getScreenWidth(activity!!)
    }

    override fun setDialogHeight(): Int {
        return DeviceUtils.getScreenHeight(activity!!)
    }

    override fun setOutSideAlpha(): Float? {
        return 1f
    }

    override fun setGravity(): Int {
        return Gravity.CENTER
    }

    override fun createView(): PtivacyFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.portal_fragment_privacy
    }

    override fun initData() {
        setHeaderTitle(activity!!.resources.getString(R.string.portal_privacy_title))
        webview.loadUrl("file:///android_asset/privacy.html")
    }

    override fun initListener() {
    }
}