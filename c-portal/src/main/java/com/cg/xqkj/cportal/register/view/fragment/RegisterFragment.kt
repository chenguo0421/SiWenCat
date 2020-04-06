package com.cg.xqkj.cportal.register.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import cn.com.cg.base.BaseDialogFragment
import cn.com.cg.ccommon.utils.Constants
import cn.com.cg.ccommon.utils.DeviceUtils
import cn.com.cg.ccommon.utils.RegexUtils
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.router.annotation.CRouter
import cn.com.cg.router.manager.RouterManager
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.register.bean.ResponsePhoneTokenBean
import com.cg.xqkj.cportal.register.bean.ResponseRegisterBean
import com.cg.xqkj.cportal.register.contract.RegisterFMContract
import com.cg.xqkj.cportal.register.presenter.RegisterFMPresenter
import kotlinx.android.synthetic.main.portal_fragment_register.*

/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:08
 *  description : { 注册-验证手机号 }
 */
@CRouter(path = "RegisterFragment")
class RegisterFragment :RegisterFMContract.IView, BaseDialogFragment<RegisterFMContract.IView, RegisterFMContract.IPresenter<RegisterFMContract.IView>>(),
    View.OnClickListener {
    private var privacyFragment: BaseDialogFragment<*, *>? = null
    private lateinit var bundle:Bundle

    var isPhoneEmpty:Boolean? = true
    var isAuthCodeEmpty:Boolean? = true
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

    override fun fragmentIOAnimation(): Int {
        return R.style.RightAnimation
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun setBundleExtra(bundle: Bundle) {
        this.bundle = bundle
    }

    override fun setDialogWidth(): Int {
        return DeviceUtils.getScreenWidth(activity!!)
    }

    override fun setDialogHeight(): Int {
        return DeviceUtils.getScreenHeight(activity!!)
    }

    override fun setOutSideAlpha(): Float? {
        return 1f
    }

    override fun setGravity(): Int {
        return Gravity.CENTER
    }

    override fun createView(): RegisterFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.portal_fragment_register
    }

    override fun initData() {
        setHeaderTitle(activity!!.resources.getString(R.string.portal_register_title))
        isPhoneEmpty = true
        isAuthCodeEmpty = true

        privacyFragment = RouterManager.getInstance().with(activity!!).fragmentTag("ptivacyFragment1").action("PtivacyFragment").navigation() as BaseDialogFragment<*,*>?
    }

    override fun initListener() {
        privacy_tv.setOnClickListener(this)
        verification_get_tv.setOnClickListener(this)
        register_tv.setOnClickListener(this)

        phone_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isPhoneEmpty = (!s?.isNotEmpty()!!) || !(RegexUtils.checkPhoneNum(s.toString().trim()))
                changeRegisterTVBackground()
            }
        })
        auth_code_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isAuthCodeEmpty = !s?.isNotEmpty()!! || (s.length != 6)
                changeRegisterTVBackground()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.privacy_tv -> {
                if (privacyFragment == null) {
                    privacyFragment = RouterManager.getInstance().with(activity!!).fragmentTag("ptivacyFragment1").action("PtivacyFragment").navigation() as BaseDialogFragment<*,*>?
                }
                privacyFragment?.show(activity!!.supportFragmentManager,"ptivacyFragment1")
            }

            R.id.verification_get_tv -> {
                if (phone_et.text != null && "" != phone_et.text.toString().trim() && RegexUtils.checkPhoneNum(phone_et.text.toString().trim())) {
                    mPresenter.getPhoneToken(activity!!,phone_et.text.toString().trim(),bindToLifecycle<Any>())
                }else{
                    ToastUtils.show(R.string.portal_register_warr_edit_phone_first)
                }
            }

            R.id.register_tv -> {
                if(checkParamsOK()){
                    mPresenter.register(activity!!,phone_et.text.toString().trim(),auth_code_et.text.toString().trim(),bindToLifecycle<Any>())
                }
            }
        }
    }

    private fun checkParamsOK(): Boolean {
        if(isPhoneEmpty!!){
            return false
        }
        if (isAuthCodeEmpty!!){
            return false
        }
        return true
    }


    fun changeRegisterTVBackground(){
        if (!isPhoneEmpty!! && !isAuthCodeEmpty!!) {
            register_tv.setBackgroundResource(R.drawable.circle_border_red)
        }else{
            register_tv.setBackgroundResource(R.drawable.circle_border_red_transparent)
        }
    }

    override fun onPhoneNumSubOK(data: ResponseRegisterBean) {
        ToastUtils.show(activity!!.getString(R.string.portal_register_phone_submit_success))
        val registerpswFragment = RouterManager.getInstance()
            .with(activity!!)
            .fragmentTag("registerpswFragment1")
            .action("RegisterpswFragment")
            .navigation() as BaseDialogFragment<*,*>?
        val bundle = Bundle()
        bundle.putString(Constants.PortalConstant.REGISTER_PHONE,phone_et.text.toString().trim())
        bundle.putString(Constants.PortalConstant.REGISTER_RANDOM_KEY,data.randomKey)
        registerpswFragment?.setBundleExtra(bundle)
        registerpswFragment?.show(activity!!.supportFragmentManager,"registerpswFragment1")
    }

    override fun onPhoneTokenOK(data: ResponsePhoneTokenBean) {
        val code = data.authCode
        ToastUtils.show(String.format(resources.getString(R.string.portal_register_getphonetoken_ok),code))
        auth_code_et.setText(code)
    }
}