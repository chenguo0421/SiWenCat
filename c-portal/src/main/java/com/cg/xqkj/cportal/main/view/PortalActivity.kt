package com.cg.xqkj.cportal.main.view

import android.content.Context
import android.view.View
import cn.com.cg.base.BaseActivity
import cn.com.cg.base.BaseDialogFragment
import cn.com.cg.base.BaseFragment
import cn.com.cg.ccommon.utils.Constants
import cn.com.cg.ccommon.utils.SharepreferenceUtils
import cn.com.cg.router.annotation.CRouter
import cn.com.cg.router.manager.RouterManager
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.contract.PortalContract
import com.cg.xqkj.cportal.main.presenter.PortalPresenter
import kotlinx.android.synthetic.main.portal_main_activity.*

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:48:43
 *  description : { 主页 }
 */
@CRouter(path = "PortalActivity")
class PortalActivity : PortalContract.IView, BaseActivity<PortalContract.IView, PortalContract.IPresenter<PortalContract.IView>>() {

    private var storeFragment: BaseFragment<*, *>? = null
    private var catholeFragment: BaseFragment<*, *>? = null
    private var homeFragment: BaseFragment<*, *>? = null
    private var giftFragment: BaseFragment<*, *>? = null
    private var myFragment: BaseFragment<*, *>? = null

    private var loginFragment: BaseDialogFragment<*, *>? = null

    private lateinit var mPresenter: PortalContract.IPresenter<PortalContract.IView>

    private var currentIndex:Int = 0

    override fun createPresenter(): PortalContract.IPresenter<PortalContract.IView> {
        mPresenter = PortalPresenter()
        return mPresenter
    }

    override fun createView(): PortalContract.IView {
        return this
    }

    override fun getBaseActivity(): Context {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.portal_main_activity
    }

    override fun initData() {
        homeFragment = RouterManager.getInstance().with(this).fragmentTag("homeFragment").action("HomeFragment").navigation() as BaseFragment<*, *>?
        replaceFragment(homeFragment!!,R.id.portal_content)
    }

    override fun initListener() {
        index_bottom_bar_store.setOnClickListener(TabOnClickListener(0))
        index_bottom_bar_cathole.setOnClickListener(TabOnClickListener(1))
        index_bottom_bar_home.setOnClickListener(TabOnClickListener(2))
        index_bottom_bar_gift.setOnClickListener(TabOnClickListener(3))
        index_bottom_bar_my.setOnClickListener(TabOnClickListener(4))
    }

    private fun changeTabByIndex(index:Int){
        when(index){
            0 -> {
                resetView()
                store_tv.setTextColor(resources.getColor(R.color.common_text_color_red))
                index_bottom_bar_store_image.setImageResource(R.mipmap.store_red)
                if (storeFragment == null) {
                    storeFragment = RouterManager.getInstance().with(this).fragmentTag("storeFragment1").action("StoreFragment").navigation() as BaseFragment<*, *>?
                }
                replaceFragment(storeFragment!!,R.id.portal_content)
            }
            1 -> {
                resetView()
                cathole_tv.setTextColor(resources.getColor(R.color.common_text_color_red))
                index_bottom_bar_cathole_image.setImageResource(R.mipmap.gathole_red)
                if (catholeFragment == null) {
                    catholeFragment = RouterManager.getInstance().with(this).fragmentTag("catholeFragment1").action("CatholeFragment").navigation() as BaseFragment<*, *>?
                }
                replaceFragment(catholeFragment!!,R.id.portal_content)
            }
            2 -> {
                resetView()
                home_tv.setTextColor(resources.getColor(R.color.common_text_color_red))
                index_bottom_bar_home.setImageResource(R.mipmap.siwencat_red)
                if (homeFragment == null) {
                    homeFragment = RouterManager.getInstance().with(this).fragmentTag("homeFragment1").action("HomeFragment").navigation() as BaseFragment<*, *>?
                }
                replaceFragment(homeFragment!!,R.id.portal_content)
            }
            3 -> {
                resetView()
                gift_tv.setTextColor(resources.getColor(R.color.common_text_color_red))
                index_bottom_bar_gift_image.setImageResource(R.mipmap.gift_red)
                if (giftFragment == null) {
                    giftFragment = RouterManager.getInstance().with(this).fragmentTag("giftFragment1").action("GiftFragment").navigation() as BaseFragment<*, *>?
                }
                replaceFragment(giftFragment!!,R.id.portal_content)
            }
            4 -> {
                if (!SharepreferenceUtils.getBoolean(this,Constants.PortalConstant.IS_LOGIN,false)){
                    if (loginFragment == null) {
                        loginFragment = RouterManager.getInstance().with(this).fragmentTag("loginFragment1").action("LoginFragment").navigation() as BaseDialogFragment<*, *>?
                    }
                    loginFragment?.show(supportFragmentManager,"loginFragment1")
                    return
                }
                resetView()
                my_tv.setTextColor(resources.getColor(R.color.common_text_color_red))
                index_bottom_bar_my_image.setImageResource(R.mipmap.my_red)
                if (myFragment == null) {
                    myFragment = RouterManager.getInstance().with(this).fragmentTag("myFragment1").action("MyFragment").navigation() as BaseFragment<*, *>?
                }
                replaceFragment(myFragment!!,R.id.portal_content)
            }
        }

        currentIndex = index
    }

    private fun resetView(){
        setAllDrawable2Gray()
        setAllTextColorGrat()
    }

    private fun setAllDrawable2Gray(){
        index_bottom_bar_store_image.setImageResource(R.mipmap.store_gray)
        index_bottom_bar_cathole_image.setImageResource(R.mipmap.gathole_gray)
        index_bottom_bar_home.setImageResource(R.mipmap.siwencat_gray)
        index_bottom_bar_gift_image.setImageResource(R.mipmap.gift_gray)
        index_bottom_bar_my_image.setImageResource(R.mipmap.my_gray)
    }

    private fun  setAllTextColorGrat(){
        store_tv.setTextColor(resources.getColor(R.color.common_text_color_cdcdcd))
        cathole_tv.setTextColor(resources.getColor(R.color.common_text_color_cdcdcd))
        home_tv.setTextColor(resources.getColor(R.color.common_text_color_cdcdcd))
        gift_tv.setTextColor(resources.getColor(R.color.common_text_color_cdcdcd))
        my_tv.setTextColor(resources.getColor(R.color.common_text_color_cdcdcd))
    }

    inner class TabOnClickListener(private val index: Int) : View.OnClickListener{

        override fun onClick(v: View?) {
            if (index == currentIndex){
                return
            }
            changeTabByIndex(index)
        }

    }

}