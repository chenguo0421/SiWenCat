package com.cg.xqkj.cportal.main.presenter

import cn.com.cg.ccommon.utils.GlobalParams
import cn.com.cg.clog.CLog
import com.cg.xqkj.cportal.main.bean.StoreProductsBean
import com.cg.xqkj.cportal.main.contract.StoreFMContract
import com.cg.xqkj.cportal.main.model.StoreFMModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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

    override fun getStoreProductsResponse() {
        doAsync {
            val response = GlobalParams.getStoreProductsResponse(getView()?.getBaseActivity()!!,"/json/assets_store_productlist.json")
            response?.let { CLog.d(it) }
            val type = object : TypeToken<ArrayList<StoreProductsBean>>(){}.type
            val list = Gson().fromJson<ArrayList<StoreProductsBean>>(response,type)
            uiThread {
                getView()?.onLoadProductListSuccess(list)
            }
        }
    }
}

