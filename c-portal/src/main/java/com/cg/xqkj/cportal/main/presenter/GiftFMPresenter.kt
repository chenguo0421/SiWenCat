package com.cg.xqkj.cportal.main.presenter

import cn.com.cg.ccommon.utils.GlobalParams
import cn.com.cg.clog.CLog
import com.cg.xqkj.cportal.main.bean.GiftDJYPBean
import com.cg.xqkj.cportal.main.bean.HomeBean
import com.cg.xqkj.cportal.main.contract.GiftFMContract
import com.cg.xqkj.cportal.main.model.GiftFMModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 *  author : ChenGuo
 *  date : 2019-11-30 13:55:15
 *  description : { 请添加该类的描述 }
 */
class GiftFMPresenter : GiftFMContract.IPresenter<GiftFMContract.IView>() {

    private var mModel:GiftFMModel? = null

    init {
        mModel = GiftFMModel()
    }

    override fun getGiftDJYPData() {
        doAsync {
            val response = GlobalParams.getGiftProductsResponse(getView()?.getBaseActivity()!!,"/json/assets_gift_djyp")
            response?.let { CLog.d(it) }
            val list = Gson().fromJson(response, GiftDJYPBean::class.java)
            uiThread {
                getView()?.onLoadGiftDJYPDataSuccess(list)
            }
        }
    }
}