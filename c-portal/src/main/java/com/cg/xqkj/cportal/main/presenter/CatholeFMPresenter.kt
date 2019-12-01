package com.cg.xqkj.cportal.main.presenter

import com.cg.xqkj.cportal.main.contract.CatholeFMContract
import com.cg.xqkj.cportal.main.model.CatholeFMModel

/**
 *  author : ChenGuo
 *  date : 2019-11-30 14:00:40
 *  description : { 请添加该类的描述 }
 */
class CatholeFMPresenter : CatholeFMContract.IPresenter<CatholeFMContract.IView>() {

    private var mModel:CatholeFMModel? = null

    init {
        mModel = CatholeFMModel()
    }
}