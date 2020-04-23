package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import cn.com.cg.base.BaseFragment
import cn.com.cg.ccommon.utils.GlobalParams
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.ccommon.widget.banner.RecyclerViewBannerBase
import cn.com.cg.router.annotation.CRouter
import cn.com.cg.router.manager.RouterManager
import com.bumptech.glide.Glide
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.adapter.StoreProductAdapter
import com.cg.xqkj.cportal.main.bean.StoreProductsBean
import com.cg.xqkj.cportal.main.contract.StoreFMContract
import com.cg.xqkj.cportal.main.presenter.StoreFMPresenter
import kotlinx.android.synthetic.main.portal_fragment_store.*
import kotlin.collections.ArrayList

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:50:54
 *  description : { 商城页面 }
 */
@CRouter(path = "StoreFragment")
class StoreFragment :StoreFMContract.IView, BaseFragment<StoreFMContract.IView, StoreFMContract.IPresenter<StoreFMContract.IView>>() {
    private lateinit var adapter: StoreProductAdapter
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

    override fun onLoadProductListSuccess(list: ArrayList<StoreProductsBean>) {
        adapter = StoreProductAdapter(activity!!,list)
        val manager = LinearLayoutManager(activity)
        product_rv.layoutManager = manager
        product_rv.adapter = adapter
    }

    override fun initListener() {
        buy_iv.setOnClickListener{
            RouterManager.getInstance().with(activity!!).action("/AnimUtils/alphaView").callMethod(it,0.5f,1f,300L)
        }
        sale_iv.setOnClickListener{
            RouterManager.getInstance().with(activity!!).action("/AnimUtils/alphaView").callMethod(it,0.5f,1f,300L)
        }
        shopcar_iv.setOnClickListener{
            RouterManager.getInstance().with(activity!!).action("/AnimUtils/alphaView").callMethod(it,0.5f,1f,300L)
        }
    }

    override fun initData() {
        initTopBanner()
        initCenterBanner()
        initProductList()
    }

    private fun initProductList() {
        mPresenter.getStoreProductsResponse()
    }


    private fun initCenterBanner() {
        Glide.with(context).load("file:///android_asset/img/assets_store_image27.png").into(center_banner_iv)
    }

    private fun initTopBanner() {
        banner.initBannerImageView(GlobalParams.getStoreBannerUrlList(), RecyclerViewBannerBase.OnBannerItemClickListener {
            ToastUtils.show("onclick : $it")
        })
    }
}