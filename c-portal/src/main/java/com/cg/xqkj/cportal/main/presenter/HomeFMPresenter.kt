package com.cg.xqkj.cportal.main.presenter

import cn.com.cg.ccommon.utils.GlobalParams
import cn.com.cg.clog.CLog
import com.cg.xqkj.cportal.main.bean.HomeBean
import com.cg.xqkj.cportal.main.contract.HomeFMContract
import com.cg.xqkj.cportal.main.model.HomeFMModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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

    override fun queryHomeData() {
        doAsync {
            val response = GlobalParams.getHomeDataResponse(getView()?.getBaseActivity()!!,"/json/assets_home_list.json")
            response?.let { CLog.d(it) }
            val list = Gson().fromJson(response,HomeBean::class.java)
            uiThread {
                getView()?.onQueryHomeDataSuccess(list)
            }
        }
    }
}