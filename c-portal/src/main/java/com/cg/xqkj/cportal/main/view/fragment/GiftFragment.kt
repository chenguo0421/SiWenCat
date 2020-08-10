package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.base.BaseFragment
import cn.com.cg.ccommon.utils.GlobalParams
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.ccommon.widget.recyclerview.NoScrollLinearLayoutManager
import cn.com.cg.router.annotation.CRouter
import com.bumptech.glide.Glide
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.adapter.GiftDJYPAdapter
import com.cg.xqkj.cportal.main.adapter.HomeListAdapter
import com.cg.xqkj.cportal.main.bean.GiftDJYPBean
import com.cg.xqkj.cportal.main.contract.GiftFMContract
import com.cg.xqkj.cportal.main.presenter.GiftFMPresenter
import kotlinx.android.synthetic.main.portal_fragment_gift.*
import kotlinx.android.synthetic.main.portal_fragment_gift.banner
import kotlinx.android.synthetic.main.portal_fragment_home.*

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:55:15
 *  description : { 礼品页 }
 */
@CRouter(path = "GiftFragment")
class GiftFragment :GiftFMContract.IView, BaseFragment<GiftFMContract.IView, GiftFMContract.IPresenter<GiftFMContract.IView>>() {
    private lateinit var giftMenuAdapter: GiftDJYPAdapter
    private lateinit var bundle: Bundle
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

    override fun onLoadGiftDJYPDataSuccess(bean: GiftDJYPBean?) {
        bean?.menus?.let {
            giftMenuAdapter = GiftDJYPAdapter(getBaseActivity(),bean.menus!!)
            var manager = GridLayoutManager(getBaseActivity(),2,GridLayoutManager.HORIZONTAL,false)
            rv_productType.layoutManager = manager
            rv_productType.adapter = giftMenuAdapter
        }
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun setBundleExtra(bundle: Bundle) {
        this.bundle = bundle
    }

    override fun createView(): GiftFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.portal_fragment_gift
    }

    override fun initListener() {
    }

    override fun initData() {
        initTopBanner()
        initProductType()
        mPresenter.getGiftDJYPData()
    }

    private fun initProductType() {
        
    }

    private fun initTopBanner() {
        Glide.with(context).load("file:///android_asset/img/assets_gift_image1.png").into(iv_header_bg)
        banner.initBannerImageView(GlobalParams.getGiftBannerUrlList()) {
            ToastUtils.show("onclick : $it")
        }
    }
}