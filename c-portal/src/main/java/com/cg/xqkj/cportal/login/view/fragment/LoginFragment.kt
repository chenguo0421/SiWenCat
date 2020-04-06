package com.cg.xqkj.cportal.login.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import cn.com.cg.base.BaseDialogFragment
import cn.com.cg.ccommon.utils.*
import cn.com.cg.router.annotation.CMethod
import cn.com.cg.router.annotation.CRouter
import cn.com.cg.router.manager.RouterManager
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.login.bean.ResponseLoginBean
import com.cg.xqkj.cportal.login.contract.LoginFMContract
import com.cg.xqkj.cportal.login.presenter.LoginFMPresenter
import kotlinx.android.synthetic.main.portal_fragment_login.*



/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:35
 *  description : { 登陆页 }
 */
@CRouter(path = "LoginFragment")
class LoginFragment :LoginFMContract.IView, BaseDialogFragment<LoginFMContract.IView,LoginFMContract.IPresenter<LoginFMContract.IView>>(),
    View.OnClickListener {
    private lateinit var bundle: Bundle
    var isUserNameEmpty:Boolean? = true
    var isPSWEmpty:Boolean? = true
    private lateinit var mPresenter: LoginFMContract.IPresenter<LoginFMContract.IView>

    override fun createPresenter(): LoginFMContract.IPresenter<LoginFMContract.IView> {
        mPresenter = LoginFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseDialogFragment<LoginFMContract.IView,LoginFMContract.IPresenter<LoginFMContract.IView>> {
        synchronized(LoginFragment::class){
            return LoginFragment()
        }
    }

    override fun fragmentIOAnimation(): Int {
        return R.style.BottomAnimation
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

    override fun createView(): LoginFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.portal_fragment_login
    }

    override fun initData() {
        show_hidden_psw_img.tag = false
        isUserNameEmpty = true
        isPSWEmpty = true
        initRemerberDataIfNeed()
    }


    @CMethod(path = "/LoginFragment/onRegisterSuccess")
    fun onRegisterSuccess(vararg params: Any){
        if (activity != null && user_et != null && user_et.text != null) {
            val username = SharepreferenceUtils.getString(
                activity!!,
                Constants.PortalConstant.REMEMBER_USERNAME,
                user_et.text.toString().trim()
            )
            user_et.setText(username)

            if (!StringUtils.isEmpty(username)){
                isUserNameEmpty = false
            }
        }
    }



    private fun initRemerberDataIfNeed() {
        if (SharepreferenceUtils.getBoolean(activity!!,Constants.PortalConstant.IS_REMEMBER_PSW,false)){
            checkbox.isChecked = true
            val username = SharepreferenceUtils.getString(activity!!,Constants.PortalConstant.REMEMBER_USERNAME,user_et.text.toString().trim())
            val psw = SharepreferenceUtils.getString(activity!!,Constants.PortalConstant.REMEMBER_PSW,psw_et.text.toString().trim())
            user_et.setText(username)
            psw_et.setText(psw)

            if (!StringUtils.isEmpty(username)){
                isUserNameEmpty = false
            }

            if (!StringUtils.isEmpty(psw)){
                isPSWEmpty = false
            }
            changeLoginTVBackground()
        }
    }

    override fun initListener() {
        show_hidden_psw_img.setOnClickListener(this)
        login_tv.setOnClickListener(this)
        register_tv.setOnClickListener(this)
        user_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isUserNameEmpty = !s?.isNotEmpty()!!
                changeLoginTVBackground()
            }
        })
        psw_et.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isPSWEmpty = !s?.isNotEmpty()!!
                changeLoginTVBackground()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v){
            show_hidden_psw_img -> {
                if (show_hidden_psw_img.tag is Boolean){
                    if(show_hidden_psw_img.tag as Boolean){
                        show_hidden_psw_img.setImageResource(R.mipmap.portal_eye_close_icon)
                        psw_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        show_hidden_psw_img.tag = false
                    }else{
                        show_hidden_psw_img.setImageResource(R.mipmap.portal_eye_open_icon)
                        psw_et.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        show_hidden_psw_img.tag = true
                    }
                }
            }

            login_tv -> {
                if(checkLoginParamsOK()){
                    mPresenter.login(activity!!,user_et.text.toString().trim(),psw_et.text.toString().trim(),bindToLifecycle<Any>())
                }
            }

            register_tv -> {
                val registerFragment = RouterManager.getInstance().with(activity!!).fragmentTag("registerFragment1").action("RegisterFragment").navigation() as BaseDialogFragment<*,*>?
                registerFragment?.show(activity!!.supportFragmentManager,"registerFragment1")
            }
        }
    }

    private fun checkLoginParamsOK(): Boolean {
        if (isUserNameEmpty!!) {
            return false
        }
        if (isPSWEmpty!!) {
            return false
        }
        return true
    }


    private fun changeLoginTVBackground() {
        if (!isUserNameEmpty!! && !isPSWEmpty!!) {
            login_tv.setBackgroundResource(R.drawable.circle_border_red)
        }else{
            login_tv.setBackgroundResource(R.drawable.circle_border_red_transparent)
        }
    }

    override fun onLoginSuccess(data: ResponseLoginBean) {
        ToastUtils.show(activity!!.resources.getString(R.string.portal_login_success))
        SharepreferenceUtils.put(activity!!,Constants.PortalConstant.REMEMBER_USERNAME,user_et.text.toString().trim())
        if (checkbox.isChecked){
            SharepreferenceUtils.put(activity!!,Constants.PortalConstant.IS_REMEMBER_PSW,true)
            SharepreferenceUtils.put(activity!!,Constants.PortalConstant.REMEMBER_PSW,psw_et.text.toString().trim())
        }else{
            SharepreferenceUtils.put(activity!!,Constants.PortalConstant.IS_REMEMBER_PSW,false)
        }
        SharepreferenceUtils.put(activity!!,Constants.PortalConstant.IS_LOGIN,true)
        dismiss()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (SharepreferenceUtils.getBoolean(activity!!,Constants.PortalConstant.IS_LOGIN,false)){
            RouterManager.getInstance()
                .with(activity!!)
                .action("/PortalActivity/changeTabByIndex")
                .callMethod(4)
        }
    }

}