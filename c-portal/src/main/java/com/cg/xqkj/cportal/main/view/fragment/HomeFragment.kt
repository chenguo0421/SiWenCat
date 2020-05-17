package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.base.BaseFragment
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.ccommon.widget.banner.RecyclerViewBannerBase
import cn.com.cg.ccommon.widget.recyclerview.NoScrollLinearLayoutManager
import cn.com.cg.router.annotation.CRouter
import com.bumptech.glide.Glide
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.adapter.HomeListAdapter
import com.cg.xqkj.cportal.main.bean.HomeBean
import com.cg.xqkj.cportal.main.contract.HomeFMContract
import com.cg.xqkj.cportal.main.presenter.HomeFMPresenter
import kotlinx.android.synthetic.main.portal_fragment_home.*
import kotlin.collections.ArrayList

/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:04
 *  description : { Home页面 }
 */
@CRouter(path = "HomeFragment")
class HomeFragment :HomeFMContract.IView, BaseFragment<HomeFMContract.IView, HomeFMContract.IPresenter<HomeFMContract.IView>>() {
    private lateinit var adapter:HomeListAdapter
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

    override fun onQueryHomeDataSuccess(bean: HomeBean) {
        setTopBanner(bean)
        setCenterBanner(bean)
        setDataList(bean)
    }

    private fun setDataList(bean: HomeBean) {
        bean.list?.let {
            adapter = HomeListAdapter(activity!!,bean.list!!)
            val manager = NoScrollLinearLayoutManager(activity!!)
            manager.orientation = RecyclerView.VERTICAL
            manager.setScrollEnabled(false)
            val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(activity!!, R.drawable.common_rv_divider_f2f2f2_ten)!!)
            rv_list.layoutManager = manager
            rv_list.adapter = adapter
            rv_list.addItemDecoration(divider)
        }
    }

    private fun setCenterBanner(bean: HomeBean) {
        bean.centerBanner?.let {
            Glide.with(activity).load(bean.centerBanner).into(center_banner_iv)
        }
    }

    private fun setTopBanner(bean: HomeBean) {
        bean.banners?.let {
            banner.initBannerImageView(it, RecyclerViewBannerBase.OnBannerItemClickListener {
                ToastUtils.show("onclick : $it")
            })
        }
    }

    override fun initListener() {
    }

    override fun initData() {
        mPresenter.queryHomeData()
    }


}