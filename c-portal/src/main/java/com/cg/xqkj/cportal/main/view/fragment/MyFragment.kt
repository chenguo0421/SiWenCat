package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.contract.MyFMContract
import com.cg.xqkj.cportal.main.presenter.MyFMPresenter

/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:52
 *  description : { 个人中心页面 }
 */
@CRouter(path = "MyFragment")
class MyFragment :MyFMContract.IView, BaseFragment<MyFMContract.IView, MyFMContract.IPresenter<MyFMContract.IView>>() {

    private lateinit var mPresenter: MyFMContract.IPresenter<MyFMContract.IView>

    override fun createPresenter(): MyFMContract.IPresenter<MyFMContract.IView> {
        mPresenter = MyFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseFragment<MyFMContract.IView, MyFMContract.IPresenter<MyFMContract.IView>> {
        synchronized(MyFragment::class){
            return MyFragment()
        }
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun createView(): MyFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.portal_fragment_my
    }

    override fun initListener() {
    }

    override fun initData() {
    }

}