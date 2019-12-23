package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import android.os.Bundle
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.contract.HomeFMContract
import com.cg.xqkj.cportal.main.presenter.HomeFMPresenter

/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:04
 *  description : { Home页面 }
 */
@CRouter(path = "HomeFragment")
class HomeFragment :HomeFMContract.IView, BaseFragment<HomeFMContract.IView, HomeFMContract.IPresenter<HomeFMContract.IView>>() {
    private lateinit var bundle:Bundle
    private lateinit var mPresenter: HomeFMContract.IPresenter<HomeFMContract.IView>

    override fun createPresenter(): HomeFMContract.IPresenter<HomeFMContract.IView> {
        mPresenter = HomeFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseFragment<HomeFMContract.IView, HomeFMContract.IPresenter<HomeFMContract.IView>> {
        synchronized(HomeFragment::class){
            return HomeFragment()
        }
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun setBundleExtra(bundle: Bundle) {
        this.bundle = bundle
    }

    override fun createView(): HomeFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.portal_fragment_home
    }

    override fun initListener() {
    }

    override fun initData() {
    }

}