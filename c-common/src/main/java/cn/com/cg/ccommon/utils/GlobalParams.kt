package cn.com.cg.ccommon.utils

import android.content.Context

object GlobalParams {

    /**
     * 商城 banner测试
     */
    fun getStoreBannerUrlList():ArrayList<String>{
        val list: java.util.ArrayList<String> =
            ArrayList()
        list.add("file:///android_asset/img/assets_store_image4.png")
        list.add("file:///android_asset/img/assets_store_image4.png")
        list.add("file:///android_asset/img/assets_store_image4.png")
        return list
    }

    /**
     * 礼品 banner测试
     */
    fun getGiftBannerUrlList():ArrayList<String>{
        val list: java.util.ArrayList<String> =
            ArrayList()
        list.add("file:///android_asset/img/assets_gift_image2.png")
        list.add("file:///android_asset/img/assets_gift_image2.png")
        list.add("file:///android_asset/img/assets_gift_image2.png")
        return list
    }

    /**
     * 商城 商品测试
     */
    fun getStoreProductsResponse(context: Context, jsonFileName:String):String?{
        return AssetsUtils.getJsonArrayString(context,jsonFileName)
    }

    /**
     * 礼品
     */
    fun getGiftProductsResponse(context: Context, jsonFileName:String):String?{
        return AssetsUtils.getJsonObjectString(context,jsonFileName)
    }

    fun getHomeDataResponse(context: Context, jsonFileName: String): String? {
        return AssetsUtils.getJsonObjectString(context,jsonFileName)
    }

}