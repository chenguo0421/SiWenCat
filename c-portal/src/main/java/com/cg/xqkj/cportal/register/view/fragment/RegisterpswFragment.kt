package com.cg.xqkj.cportal.register.view.fragment

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import cn.com.cg.base.BaseDialogFragment
import cn.com.cg.base.intf.EnterAnimType
import cn.com.cg.ccommon.utils.RegexUtils
import cn.com.cg.router.annotation.CRouter
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.register.contract.RegisterpswFMContract
import com.cg.xqkj.cportal.register.presenter.RegisterpswFMPresenter
import kotlinx.android.synthetic.main.portal_fragment_register_psw.*

/**
 *  author : ChenGuo
 *  date : 2019-12-09 21:20:34
 *  description : { 注册-设置密码 }
 */
@CRouter(path = "RegisterpswFragment")
class RegisterpswFragment :RegisterpswFMContract.IView, BaseDialogFragment<RegisterpswFMContract.IView, RegisterpswFMContract.IPresenter<RegisterpswFMContract.IView>>(),
    View.OnClickListener {

    private var isPSWEmpty: Boolean = true
    private var isConfirmPSWEmpty: Boolean = true
    private lateinit var mPresenter: RegisterpswFMContract.IPresenter<RegisterpswFMContract.IView>

    override fun createPresenter(): RegisterpswFMContract.IPresenter<RegisterpswFMContract.IView> {
        mPresenter = RegisterpswFMPresenter()
        return mPresenter
    }

    override fun getInstance(): BaseDialogFragment<RegisterpswFMContract.IView, RegisterpswFMContract.IPresenter<RegisterpswFMContract.IView>> {
        synchronized(RegisterpswFragment::class){
            return RegisterpswFragment()
        }
    }

    override fun isEnterAnimSlideToUp(): EnterAnimType {
        return EnterAnimType.RIGHT_TO_LEFT
    }

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun createView(): RegisterpswFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.portal_fragment_register_psw
    }

    override fun initData() {
        setHeaderTitle(activity!!.resources.getString(R.string.portal_register_psw_title))
        isPSWEmpty = true
        isConfirmPSWEmpty = true
    }

    override fun initListener() {
        submit_tv.setOnClickListener(this)
        psw_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isPSWEmpty = (!s?.isNotEmpty()!!) || !(RegexUtils.checkPSW(s.toString().trim()))
                changeSubmitTVBackground()
            }
        })
        confirm_psw_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isConfirmPSWEmpty = (!s?.isNotEmpty()!!) || !(RegexUtils.checkPSW(s.toString().trim()) || s.toString().trim() != psw_et.text.toString().trim())
                changeSubmitTVBackground()
            }
        })
    }

    private fun changeSubmitTVBackground() {
        if (!isPSWEmpty!! && !isConfirmPSWEmpty!!) {
            submit_tv.setBackgroundResource(R.drawable.circle_border_red)
        }else{
            submit_tv.setBackgroundResource(R.drawable.circle_border_red_transparent)
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.submit_tv -> {
                mPresenter.submitPSW(activity!!,psw_et.text.toString().trim(),confirm_psw_et.text.toString().trim(),bindToLifecycle<Any>())
            }
        }
    }

}