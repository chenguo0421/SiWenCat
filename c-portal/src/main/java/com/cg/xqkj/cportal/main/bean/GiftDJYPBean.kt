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
    var giftList:ArrayList<GiftTypeItem>? = null


    class InnerItem{
        var type : Int = 0 // 0  or  1,
        var title : String? = null // "东京优品",
        var hotText : String? = null // "品牌正品",
        var hotTextColor : String? = null // "#FFFFFFFF",
        var hotTextBGColor : String? = null // "#FFE74B4B",
        var url:ArrayList<String>? = null // ["file:///android_asset/img/assets_gift_image3.png","file:///android_asset/img/assets_gift_image4.png"]
    }

    class GiftTypeItem{
        var giftTypeText : String? = null // "家居良品",
        var giftTypeTextDis : String? = null // "新品配购",
        var gifts:ArrayList<GiftItem>? = null
        var isSelect:Boolean = false

        class GiftItem{
            var rowIndex : Int? = null // 1,
            var colIndex : Int? = null // 2,
            var convertNum = 0 // 兑换人数
            var name : String? = null // "吸盘牙刷架免打孔套装 吸壁式卫生间 于是壁挂式洗漱架用品",
            var url : ArrayList<String>? = null // ["file:///android_asset/img/assets_store_image13.png"]
            var price : String? = null // "159.9"
        }
    }
}