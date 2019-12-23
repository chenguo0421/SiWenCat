package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import android.os.Bundle
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.contract.StoreFMContract
import com.cg.xqkj.cportal.main.presenter.StoreFMPresenter

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:50:54
 *  description : { 商城页面 }
 */
@CRouter(path = "StoreFragment")
class StoreFragment :StoreFMContract.IView, BaseFragment<StoreFMContract.IView, StoreFMContract.IPresenter<StoreFMContract.IView>>() {
    private lateinit var bundle: Bundle
    private lateinit var mPresenter: StoreFMContract.IPresenter<StoreFMContract.IView>

    override fun createPresenter(): StoreFMContract.IPresenter<StoreFMContract.IView> {
        mPresenter = StoreFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseFragment<StoreFMContract.IView, StoreFMContract.IPresenter<StoreFMContract.IView>> {
        synchronized(StoreFragment::class){
            return StoreFragment()
        }
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun setBundleExtra(bundle: Bundle) {
        this.bundle = bundle
    }

    override fun createView(): StoreFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.portal_fragment_store
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}