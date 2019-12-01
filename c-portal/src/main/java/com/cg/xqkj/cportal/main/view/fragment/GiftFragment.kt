package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.contract.GiftFMContract
import com.cg.xqkj.cportal.main.presenter.GiftFMPresenter

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:55:15
 *  description : { 礼品页 }
 */
@CRouter(path = "GiftFragment")
class GiftFragment :GiftFMContract.IView, BaseFragment<GiftFMContract.IView, GiftFMContract.IPresenter<GiftFMContract.IView>>() {

    private lateinit var mPresenter: GiftFMContract.IPresenter<GiftFMContract.IView>

    override fun createPresenter(): GiftFMContract.IPresenter<GiftFMContract.IView> {
        mPresenter = GiftFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseFragment<GiftFMContract.IView, GiftFMContract.IPresenter<GiftFMContract.IView>> {
        synchronized(GiftFragment::class){
            return GiftFragment()
        }
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun createView(): GiftFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.gift_fragment
    }

    override fun initListener() {
    }

    override fun initData() {
    }

}