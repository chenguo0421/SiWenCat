package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import android.os.Bundle
import cn.com.cg.base.BaseFragment
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.ccommon.widget.banner.RecyclerViewBannerBase
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.contract.HomeFMContract
import com.cg.xqkj.cportal.main.presenter.HomeFMPresenter
import kotlinx.android.synthetic.main.portal_fragment_home.*
import java.util.*

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
        val list: MutableList<String> =
            ArrayList()
        list.add("http://img0.imgtn.bdimg.com/it/u=1352823040,1166166164&fm=27&gp=0.jpg")
        list.add("http://img3.imgtn.bdimg.com/it/u=2293177440,3125900197&fm=27&gp=0.jpg")
        list.add("http://img3.imgtn.bdimg.com/it/u=3967183915,4078698000&fm=27&gp=0.jpg")
        list.add("http://img0.imgtn.bdimg.com/it/u=3184221534,2238244948&fm=27&gp=0.jpg")
        list.add("http://img4.imgtn.bdimg.com/it/u=1794621527,1964098559&fm=27&gp=0.jpg")
        list.add("http://img4.imgtn.bdimg.com/it/u=1243617734,335916716&fm=27&gp=0.jpg")
        banner.initBannerImageView(list, RecyclerViewBannerBase.OnBannerItemClickListener {
            ToastUtils.show("onclick : $it")
        })

    }

}