package com.cg.xqkj.cportal.main.presenter

import com.cg.xqkj.cportal.main.contract.StoreFMContract
import com.cg.xqkj.cportal.main.model.StoreFMModel

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:50:54
 *  description : { 请添加该类的描述 }
 */
class StoreFMPresenter : StoreFMContract.IPresenter<StoreFMContract.IView>() {

    private var mModel:StoreFMModel? = null

    init {
        mModel = StoreFMModel()
    }
}