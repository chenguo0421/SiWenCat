package cn.com.cg.testmodule1.view

import android.content.Context
import android.view.View
import cn.com.cg.base.BaseActivity
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CRouter
import cn.com.cg.router.manager.RouterManager
import cn.com.cg.testmodule1.R
import cn.com.cg.testmodule1.contract.TestFMContract
import cn.com.cg.testmodule1.presenter.TestFMPresenter
import kotlinx.android.synthetic.main.fm_activity.*

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/9/9 14:59
 */
@CRouter("TestFMActivity")
class TestFMActivity :TestFMContract.IView, BaseActivity<TestFMContract.IView,TestFMContract.IPresenter<TestFMContract.IView>>(), View.OnClickListener {
    override fun initListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var fm1: BaseFragment<*,*>? = null
    var fm2: BaseFragment<*,*>? = null

    private lateinit var mPresenter: TestFMContract.IPresenter<TestFMContract.IView>

    override fun getBaseActivity(): Context {
        return this
    }

    override fun createPresenter(): TestFMContract.IPresenter<TestFMContract.IView> {
        mPresenter = TestFMPresenter()
        return mPresenter
    }

    override fun createView(): TestFMContract.IView {
        return this
    }

    override fun initLayoutId(): Int {
        return R.layout.fm_activity
    }

    override fun initData() {
        fm1 = RouterManager.getInstance().with(this).fragmentTag("tag1").action("MyFragment").navigation() as BaseFragment<*,*>?
        fm2 = RouterManager.getInstance().with(this).fragmentTag("tag2").action("MyFragment").navigation() as BaseFragment<*,*>?
        showBtn1.setOnClickListener(this)
        showBtn2.setOnClickListener(this)
        showBtn3.setOnClickListener(this)
        showBtn4.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.showBtn1 -> replaceFragment(fm1!!, R.id.fm1)
            R.id.showBtn2 -> replaceFragment(fm2!!, R.id.fm2)
            R.id.showBtn3 -> callMyFragmentMethodByTag("tag1")
            R.id.showBtn4 -> callMyFragmentMethodByTag("tag2")
        }
    }

    private fun callMyFragmentMethodByTag(tag: String) {
        var msg:String? = null
        when(tag){
            "tag1" -> {
                msg = "我是第一个对象"
            }
            "tag2" -> {
                msg = "我是第二个对象"
            }
        }
        RouterManager.getInstance()
            .with(this)
            .fragmentTag(tag)
            .action("/MyFragment/changeText")
            .callMethod(msg!!)
    }




}