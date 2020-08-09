package com.cg.xqkj.cportal.main.bean

/**
 *  author : chenguo
 *  date : 2020/8/9 17:48
 *  description : { 请添加该类的描述 }
 */
class GiftDJYPBean {
    var banners:ArrayList<String>? = null
    var centerBanner:String? = null
    var menus:ArrayList<InnerItem>? = null

    class InnerItem{
        var type : Int? = 0 // 0  or  1,
        var title : String? = null // "东京优品",
        var hotText : String? = null // "品牌正品",
        var hotTextColor : String? = null // "#FFFFFFFF",
        var hotTextBGColor : String? = null // "#FFE74B4B",
        var url:ArrayList<String>? = null // ["file:///android_asset/img/assets_gift_image3.png","file:///android_asset/img/assets_gift_image4.png"]
    }
}