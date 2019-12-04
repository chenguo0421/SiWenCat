package cn.com.cg.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.com.cg.mvp.base.BasePresenter
import cn.com.cg.mvp.base.intf.BaseView
import cn.com.cg.router.manager.path.RouterBeanManager
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/27 20:41
 */
open abstract class BaseFragment<V: BaseView,P: BasePresenter<V>> : RxFragment(){

    open var fragmentTag:String? = ""
    private var mView: V? = null
    private var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (mPresenter == null) {
            mPresenter = createPresenter()
        }

        if (mView == null) {
            mView = createView()
        }

        mPresenter?.attachView(mView!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(initLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RouterBeanManager.getInstance().registerFM(this)
        initData()
        initListener()
    }


    abstract fun createPresenter(): P
    abstract fun createView(): V
    protected abstract fun initLayoutId(): Int
    abstract fun initData()
    abstract fun initListener()
    abstract fun getInstance():BaseFragment<V,P>


    override fun onDestroy() {
        RouterBeanManager.getInstance().unRegisterFM(this)
        mPresenter?.detachView()
        super.onDestroy()
    }
}