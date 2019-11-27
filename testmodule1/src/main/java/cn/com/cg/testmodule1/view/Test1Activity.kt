package cn.com.cg.testmodule1.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.com.cg.base.BaseActivity
import cn.com.cg.router.annotation.CMethod
import cn.com.cg.router.annotation.CRouter
import cn.com.cg.router.manager.RouterManager
import cn.com.cg.router.manager.intf.RouterCallBack
import cn.com.cg.testmodule1.R
import cn.com.cg.testmodule1.contract.Test1Contract
import cn.com.cg.testmodule1.presenter.Test1Presenter
import kotlinx.android.synthetic.main.t1_activity.*

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/27 17:04
 */

@CRouter(path = "Test1Activity")
class Test1Activity :Test1Contract.IView, BaseActivity<Test1Contract.IView,Test1Contract.IPresenter<Test1Contract.IView>>(), View.OnClickListener, RouterCallBack {
    override fun initListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var mPresenter: Test1Contract.IPresenter<Test1Contract.IView>

    override fun createPresenter(): Test1Contract.IPresenter<Test1Contract.IView> {
        mPresenter = Test1Presenter()
        return mPresenter
    }

    override fun createView(): Test1Contract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.t1_activity
    }

    override fun initData() {

    }

    override fun getBaseActivity(): Context {
        return this
    }

    /**
     * 通过接口方法回调
     */
    override fun onCallBack(data: Any) {
        var msg = data as String
        back_tv.text = msg
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn1 -> gotoT2()
            R.id.btn2 -> gotoFMActivity()
        }
    }




    private fun gotoFMActivity() {
        RouterManager.getInstance()
            .with(this)
            .action("TestFMActivity")
            .navigation()
    }


    /**
     * 通过注解反射跳转到Test2Activity
     */
    private fun gotoT2() {
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        RouterManager.getInstance()
            .with(this)
            .sharedElement(img)
            .anim(R.anim.slide_in_left, R.anim.slide_out_right)
            .action("Test2Activity")
            .intent(intent)
            .setCallBack(this)
            .navigation()
    }



    /**
     * 通过注解反射回调
     */
    @CMethod("/Test1Activity/onT2CallBack")
    fun onT2InvokeCallBack(vararg params:Any): String {
        back_tv.text = params[0].toString()
        return "hello t2,i am t1"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
    }

}


