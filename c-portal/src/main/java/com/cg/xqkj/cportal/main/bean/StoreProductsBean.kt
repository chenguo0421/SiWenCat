package com.cg.xqkj.cportal.main.bean

class StoreProductsBean {
    var type:Int? = null
    var title:ArrayList<ProductTitle>? = null
    var products:ArrayList<Product>? = null
    
    class Product{
      var rowIndex : Int? = null // 1,
      var colIndex : Int? = null // 2,
      var productTypeName : String? = null //"好物推荐",
      var advText : String? = null // "搜索全球好货",
      var name : String? = null // "欧诗漫美白护肤套装",
      var url : ArrayList<String>? = null // ["file:///android_asset/img/assets_store_image10.png"],
      var price : String? = null // "159.9"
    }
    
    class ProductTitle{
      var rowIndex : Int? = null // 1,
      var colIndex : Int? = null // 1,
      var title : String? = null // "新人专属",
      var titleColor : String? = null // "#FF000000",
      var hotText : String? = null // "NEW",
      var hotTextColor : String? = null // "#FFFFFFFF",
      var hotTextBGColor : String? = null // "#FFE74B4B",
      var nextText : String? = null // "点击进入兑换",
      var nextTextColor : String? = null // "#FF999999"
    }
}