package cn.com.cg.testmodule2.view

import android.content.Context
import cn.com.cg.base.BaseActivity
import cn.com.cg.router.annotation.CRouter
import cn.com.cg.router.manager.RouterManager
import cn.com.cg.testmodule2.R
import cn.com.cg.testmodule2.contract.Test2Contract
import cn.com.cg.testmodule2.presenter.Test2Presenter
import kotlinx.android.synthetic.main.t2_activity.*

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/27 17:05
 */
@CRouter("Test2Activity")
class Test2Activity :Test2Contract.IView, BaseActivity<Test2Contract.IView,Test2Contract.IPresenter<Test2Contract.IView>>(){
    override fun initListener() {
    }

    private lateinit var mPresenter: Test2Contract.IPresenter<Test2Contract.IView>

    override fun createPresenter(): Test2Contract.IPresenter<Test2Contract.IView> {
        mPresenter = Test2Presenter()
        return mPresenter
    }

    override fun createView(): Test2Contract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.t2_activity
    }

    override fun initData() {
        btn1.setOnClickListener {
            when(it?.id){
                R.id.btn1 -> resultData()
            }
        }

        btn2.setOnClickListener {
            callMethodByMethodPath()
        }

        btn3.setOnClickListener {
            callUtilsMethodByMethodPath()
        }
    }

    override fun getBaseActivity(): Context {
        return this
    }




    /**
     * 通过方法注解向Test1Activity回调数据
     */
    private fun callMethodByMethodPath() {
        val msg = RouterManager.getInstance()
            .with(this)
            .action("/Test1Activity/onT2CallBack")
            .callMethod("hello word")
        tv2.text = msg.toString()
    }

    /**
     * 通过方法注解向Test1Activity回调数据
     */
    private fun callUtilsMethodByMethodPath() {
        val msg = RouterManager.getInstance()
            .with(this)
            .action("/MyUtils/testCallUtilsMethod")
            .callMethod("hello word")
        tv2.text = msg.toString()
    }


    /**
     * 通过接口ID向Test1Activity回调数据
     */
    private fun resultData() {
        var callbackData: String = "我来自第二页"
        RouterManager.getInstance()
            .onCallBack(callBackMethodID,callbackData)
        RouterManager.getInstance().with(this).finish()
//        overridePendingTransition(0,0)
    }


}