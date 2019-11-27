package cn.com.cg.testmodule2.view.fragment

import android.content.Context
import android.util.Log
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CMethod
import cn.com.cg.router.annotation.CRouter
import cn.com.cg.testmodule2.R
import cn.com.cg.testmodule2.contract.MyFMContract
import cn.com.cg.testmodule2.presenter.MyFMPresenter
import kotlinx.android.synthetic.main.fm_my.*

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/9/9 10:34
 */
@CRouter("MyFragment")
class MyFragment :MyFMContract.IView, BaseFragment<MyFMContract.IView,MyFMContract.IPresenter<MyFMContract.IView>>() {
    override fun initData() {
    }

    override fun initListener() {
    }

    private lateinit var mPresenter: MyFMContract.IPresenter<MyFMContract.IView>

    override fun getBaseActivity(): Context {
        return activity!!
    }

    override fun createPresenter(): MyFMContract.IPresenter<MyFMContract.IView> {
        mPresenter = MyFMPresenter()
        return mPresenter
    }

    override fun createView(): MyFMContract.IView {
        return this
    }



    override fun initLayoutId(): Int {
        return R.layout.fm_my
    }

    override fun getInstance(): BaseFragment<MyFMContract.IView,MyFMContract.IPresenter<MyFMContract.IView>> {
        synchronized(MyFragment::class){
            return MyFragment()
        }
    }


    @CMethod("/MyFragment/changeText")
    fun changeText(vararg params:Any){
        Log.e("changeText", "params = $params")
        tv.text = params[0].toString()

    }

}