package com.cg.xqkj.cportal.main.presenter

import com.cg.xqkj.cportal.main.contract.HomeFMContract
import com.cg.xqkj.cportal.main.model.HomeFMModel

/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:04
 *  description : { 请添加该类的描述 }
 */
class HomeFMPresenter : HomeFMContract.IPresenter<HomeFMContract.IView>() {

    private var mModel:HomeFMModel? = null

    init {
        mModel = HomeFMModel()
    }
}