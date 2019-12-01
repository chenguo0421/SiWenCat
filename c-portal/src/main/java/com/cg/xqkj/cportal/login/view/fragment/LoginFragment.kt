package com.cg.xqkj.cportal.login.view.fragment

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import cn.com.cg.base.BaseDialogFragment
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.login.contract.LoginFMContract
import com.cg.xqkj.cportal.login.presenter.LoginFMPresenter
import kotlinx.android.synthetic.main.login_fragment.*



/**
 *  author : ChenGuo
 *  date : 2019-11-30 19:47:35
 *  description : { 请添加该类的描述 }
 */
@CRouter(path = "LoginFragment")
class LoginFragment :LoginFMContract.IView, BaseDialogFragment<LoginFMContract.IView, LoginFMContract.IPresenter<LoginFMContract.IView>>(),
    View.OnClickListener {

    var isUserNameEmpty:Boolean? = true
    var isPSWEmpty:Boolean? = true

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
       }
    }

    private fun checkLoginParamsOK(): Boolean {
        if("" == user_et.text.toString().trim()){
            return false
        }
        if ("" == psw_et.text.toString().trim()){
            return false
        }
        return true
    }

    private lateinit var mPresenter: LoginFMContract.IPresenter<LoginFMContract.IView>

    override fun createPresenter(): LoginFMContract.IPresenter<LoginFMContract.IView> {
        mPresenter = LoginFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseDialogFragment<LoginFMContract.IView, LoginFMContract.IPresenter<LoginFMContract.IView>> {
        synchronized(LoginFragment::class){
            return LoginFragment()
        }
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun createView(): LoginFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.login_fragment
    }

    override fun initData() {
        show_hidden_psw_img.tag = false
        isUserNameEmpty = true
        isPSWEmpty = true
    }

    override fun initListener() {
        show_hidden_psw_img.setOnClickListener(this)
        login_tv.setOnClickListener(this)
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


    private fun changeLoginTVBackground() {
        if (!isUserNameEmpty!! && !isPSWEmpty!!) {
            login_tv.setBackgroundResource(R.drawable.circle_border_red)
        }else{
            login_tv.setBackgroundResource(R.drawable.circle_border_red_transparent)
        }
    }
}