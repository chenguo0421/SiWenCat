package cn.com.cg.testmodule1.presenter

import cn.com.cg.testmodule1.contract.TestFMContract
import cn.com.cg.testmodule1.model.TestFMModel

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/26 9:29
 */
class TestFMPresenter: TestFMContract.IPresenter<TestFMContract.IView>() {
    private var mModel:TestFMModel? = null

    init {
        mModel = TestFMModel()
    }
}