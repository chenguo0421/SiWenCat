package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.adapter.MyFunAdapter
import com.cg.xqkj.cportal.main.adapter.MyToolsAdapter
import com.cg.xqkj.cportal.main.bean.MyMenus
import com.cg.xqkj.cportal.main.contract.MyFMContract
import com.cg.xqkj.cportal.main.presenter.MyFMPresenter
import kotlinx.android.synthetic.main.portal_fragment_my.*

/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:52
 *  description : { 个人中心页面 }
 */
@CRouter(path = "MyFragment")
class MyFragment :MyFMContract.IView, BaseFragment<MyFMContract.IView, MyFMContract.IPresenter<MyFMContract.IView>>() {
    private var funAdapter: MyFunAdapter? = null
    private var toolAdapter: MyToolsAdapter? = null
    private lateinit var bundle: Bundle
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

    override fun setBundleExtra(bundle: Bundle) {
        this.bundle = bundle
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
        initToolsRecyclerViews()
        initFunRecyclerViews()
    }

    private fun initFunRecyclerViews() {
        val manager =
            LinearLayoutManager(context)
        function_rv.layoutManager = manager
        funAdapter = MyFunAdapter(activity, MyMenus.getFunMenus(activity!!))
        function_rv.adapter = funAdapter
    }

    private fun initToolsRecyclerViews() {
        val manager: GridLayoutManager =
            GridLayoutManager(activity!!, 4, RecyclerView.VERTICAL, false)
        tools_menu_rv.layoutManager = manager
        toolAdapter = MyToolsAdapter(activity, MyMenus.getMyToolsMenus(activity!!))
        tools_menu_rv.adapter = toolAdapter
    }

}