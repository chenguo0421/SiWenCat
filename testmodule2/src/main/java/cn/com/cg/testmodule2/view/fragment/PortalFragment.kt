package cn.com.cg.testmodule2.view.fragment

import android.content.Context
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CRouter
import cn.com.cg.testmodule2.R
import cn.com.cg.testmodule2.contract.PortalFMContract
import cn.com.cg.testmodule2.presenter.PortalFMPresenter

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/9/9 10:35
 */
@CRouter("PortalFragment")
class PortalFragment :PortalFMContract.IView, BaseFragment<PortalFMContract.IView,PortalFMContract.IPresenter<PortalFMContract.IView>>(){
    override fun initData() {
    }

    override fun initListener() {
    }


    private lateinit var mPresenter: PortalFMContract.IPresenter<PortalFMContract.IView>

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun createPresenter(): PortalFMContract.IPresenter<PortalFMContract.IView> {
        mPresenter = PortalFMPresenter()
        return mPresenter
    }

    override fun createView(): PortalFMContract.IView {
        return this
    }


    override fun initLayoutId(): Int {
        return R.layout.fm_portal
    }

    override fun getInstance(): BaseFragment<PortalFMContract.IView,PortalFMContract.IPresenter<PortalFMContract.IView>> {
        synchronized(PortalFragment::class){
            return PortalFragment()
        }
    }


}