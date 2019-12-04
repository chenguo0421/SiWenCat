package cn.com.cg.mvp.base

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/25 15:05
 */
abstract class BasePresenter<V> {
    private var mView: V? = null

    fun getView(): V? {
        return mView
    }

    fun attachView(view: V) {
        mView = view
    }

    fun detachView() {
        mView = null
    }
}