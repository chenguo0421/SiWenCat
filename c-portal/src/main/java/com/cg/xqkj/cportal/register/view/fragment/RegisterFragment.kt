package com.cg.xqkj.cportal.register.view.fragment

import android.content.Context
import android.view.View
import cn.com.cg.base.BaseDialogFragment
import cn.com.cg.ccommon.utils.RegexUtils
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.router.annotation.CRouter
import cn.com.cg.router.manager.RouterManager
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.register.contract.RegisterFMContract
import com.cg.xqkj.cportal.register.presenter.RegisterFMPresenter
import kotlinx.android.synthetic.main.register_fragment.*

/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:08
 *  description : { 请添加该类的描述 }
 */
@CRouter(path = "RegisterFragment")
class RegisterFragment :RegisterFMContract.IView, BaseDialogFragment<RegisterFMContract.IView, RegisterFMContract.IPresenter<RegisterFMContract.IView>>(),
    View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.privacy_tv -> {
                val privacyFragment = RouterManager.getInstance().with(activity!!).fragmentTag("ptivacyFragment1").action("PtivacyFragment").navigation() as BaseDialogFragment<*,*>?
                privacyFragment?.show(activity!!.supportFragmentManager,"ptivacyFragment1")
            }

            R.id.verification_get_tv -> {
                if (phone_et.text != null && "" != phone_et.text.toString().trim() && RegexUtils.checkPhoneNum(phone_et.text.toString().trim())) {

                }else{
                    ToastUtils.show(R.string.portal_register_warr_edit_phone_first)
                }
            }
        }
    }

    private lateinit var mPresenter: RegisterFMContract.IPresenter<RegisterFMContract.IView>

    override fun createPresenter(): RegisterFMContract.IPresenter<RegisterFMContract.IView> {
        mPresenter = RegisterFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseDialogFragment<RegisterFMContract.IView, RegisterFMContract.IPresenter<RegisterFMContract.IView>> {
        synchronized(RegisterFragment::class){
            return RegisterFragment()
        }
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun createView(): RegisterFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.register_fragment
    }

    override fun initData() {
        setHeaderTitle(activity!!.resources.getString(R.string.portal_register_title))
    }

    override fun initListener() {
        privacy_tv.setOnClickListener(this)
        verification_get_tv.setOnClickListener(this)
    }
}