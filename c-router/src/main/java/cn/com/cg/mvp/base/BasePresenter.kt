package cn.com.cg.mvp.base

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/25 15:05
 */
abstract class BasePresenter<BaseView> {
    private var mView: BaseView? = null

    fun getView(): BaseView? {
        return mView
    }

    fun attachView(view: BaseView) {
        mView = view
    }

    fun detachView() {
        mView = null
    }
}