package cn.com.cg.testmodule2.presenter

import cn.com.cg.testmodule2.contract.Test2Contract
import cn.com.cg.testmodule2.model.Test2Model

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/26 9:34
 */
class Test2Presenter: Test2Contract.IPresenter<Test2Contract.IView>() {
    private var mModel: Test2Model? = null

    init {
        mModel = Test2Model()
    }
}