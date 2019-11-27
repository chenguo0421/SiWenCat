package cn.com.cg.testmodule1.presenter

import cn.com.cg.testmodule1.contract.Test1Contract
import cn.com.cg.testmodule1.model.Test1Model

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/26 9:21
 */
class Test1Presenter: Test1Contract.IPresenter<Test1Contract.IView>() {
    private var mModel: Test1Model? = null

    init {
        mModel = Test1Model()
    }
}