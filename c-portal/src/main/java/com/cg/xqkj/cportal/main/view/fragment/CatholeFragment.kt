package com.cg.xqkj.cportal.main.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.contract.CatholeFMContract
import com.cg.xqkj.cportal.main.presenter.CatholeFMPresenter
import kotlinx.android.synthetic.main.portal_fragment_gathole.*

/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:40
 *  description : { 猫屋页面 }
 */
@CRouter(path = "CatholeFragment")
class CatholeFragment :CatholeFMContract.IView, BaseFragment<CatholeFMContract.IView, CatholeFMContract.IPresenter<CatholeFMContract.IView>>(),
    View.OnClickListener {
    private lateinit var bundle: Bundle
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

    override fun setBundleExtra(bundle: Bundle) {
        this.bundle = bundle
    }

    override fun createView(): CatholeFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.portal_fragment_gathole
    }

    override fun initListener() {
    }

    override fun initData() {
        iv_collect.setOnClickListener(this)
        iv_original.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_collect -> {
                iv_collect.setBackgroundResource(R.drawable.common_circle_half_left_red)
                iv_collect.setTextColor(getBaseActivity().getColor(R.color.common_white))
                iv_original.setBackgroundResource(R.drawable.common_circle_half_border_right_red)
                iv_original.setTextColor(getBaseActivity().getColor(R.color.common_red))
            }
            R.id.iv_original -> {
                iv_collect.setBackgroundResource(R.drawable.common_circle_half_border_left_red)
                iv_collect.setTextColor(getBaseActivity().getColor(R.color.common_red))
                iv_original.setBackgroundResource(R.drawable.common_circle_half_right_red)
                iv_original.setTextColor(getBaseActivity().getColor(R.color.common_white))
            }
        }
    }

}