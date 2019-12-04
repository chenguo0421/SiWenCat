package cn.com.cg.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cn.com.cg.mvp.base.BasePresenter
import cn.com.cg.mvp.base.intf.BaseView
import cn.com.cg.router.manager.params.RouterParamsManager
import cn.com.cg.router.manager.path.RouterBeanManager
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/27 20:22
 */
open abstract class BaseActivity<V:BaseView,P: BasePresenter<V>> : RxAppCompatActivity() {

    open var callBackMethodID: String? = null
    private var mView: V? = null
    private var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayoutId())

        RouterBeanManager.getInstance().registerAct(this)
        callBackMethodID = intent.getStringExtra(RouterParamsManager.METHODCALLBACKID)

        if (mPresenter == null) {
            mPresenter = createPresenter()
        }

        if (mView == null) {
            mView = createView()
        }

        mPresenter?.attachView(mView!!)

        initData()
        initListener()
    }


    abstract fun createPresenter(): P
    abstract fun createView(): V
    abstract fun initLayoutId(): Int
    abstract fun initData()
    abstract fun initListener()



    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction{replace(frameId, fragment)}
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) = beginTransaction().func().commit()

    public override fun onDestroy() {
        RouterBeanManager.getInstance().unRegisterAct(this)
        mPresenter?.detachView()
        super.onDestroy()
    }
}