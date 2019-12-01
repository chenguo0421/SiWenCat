package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.contract.CatholeFMContract
import com.cg.xqkj.cportal.main.presenter.CatholeFMPresenter

/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:40
 *  description : { 猫屋页面 }
 */
@CRouter(path = "CatholeFragment")
class CatholeFragment :CatholeFMContract.IView, BaseFragment<CatholeFMContract.IView, CatholeFMContract.IPresenter<CatholeFMContract.IView>>() {

    private lateinit var mPresenter: CatholeFMContract.IPresenter<CatholeFMContract.IView>

    override fun createPresenter(): CatholeFMContract.IPresenter<CatholeFMContract.IView> {
        mPresenter = CatholeFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseFragment<CatholeFMContract.IView, CatholeFMContract.IPresenter<CatholeFMContract.IView>> {
        synchronized(CatholeFragment::class){
            return CatholeFragment()
        }
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun createView(): CatholeFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.gathole_fragment
    }

    override fun initListener() {
    }

    override fun initData() {
    }

}