package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.base.BaseFragment
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.adapter.MyFunAdapter
import com.cg.xqkj.cportal.main.adapter.MyFunDetailAdapter
import com.cg.xqkj.cportal.main.adapter.MyToolsAdapter
import com.cg.xqkj.cportal.main.bean.MyMenus
import com.cg.xqkj.cportal.main.contract.MyFMContract
import com.cg.xqkj.cportal.main.presenter.MyFMPresenter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.portal_fragment_my.*


/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:52
 *  description : { 个人中心页面 }
 */
@CRouter(path = "MyFragment")
class MyFragment :MyFMContract.IView, BaseFragment<MyFMContract.IView, MyFMContract.IPresenter<MyFMContract.IView>>(),
    MyToolsAdapter.OnToolsItemClickListener, MyFunDetailAdapter.OnFunDetailItemClickListener {
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
        refreshLayout.setRefreshHeader(ClassicsHeader(getBaseActivity()))
        refreshLayout.setRefreshFooter(ClassicsFooter(getBaseActivity()))
        refreshLayout.setOnRefreshListener { refreshlayout ->
            refreshlayout.finishRefresh(2000 /*,false*/) //传入false表示刷新失败
        }
        refreshLayout.setOnLoadMoreListener { refreshlayout ->
            refreshlayout.finishLoadMore(2000 /*,false*/) //传入false表示加载失败
        }
    }

    private fun initFunRecyclerViews() {
        val manager =
            LinearLayoutManager(context)
        function_rv.layoutManager = manager
        funAdapter = MyFunAdapter(activity, MyMenus.getFunMenus(activity!!),this)
        function_rv.adapter = funAdapter
    }

    private fun initToolsRecyclerViews() {
        val manager =
            GridLayoutManager(activity!!, 4, RecyclerView.VERTICAL, false)
        tools_menu_rv.layoutManager = manager
        toolAdapter = MyToolsAdapter(activity!!, MyMenus.getMyToolsMenus(activity!!),this)
        tools_menu_rv.adapter = toolAdapter
    }

    override fun onToolItemClick(position: Int) {
        ToastUtils.show("点击了：" + toolAdapter?.getItemData(position)?.name)
    }

    override fun onFunDetailItemClick(parentPosition: Int, position: Int) {
        ToastUtils.show("点击了：" + (funAdapter?.getItemData(parentPosition)?.contentMenu?.get(position)?.name))
    }

}