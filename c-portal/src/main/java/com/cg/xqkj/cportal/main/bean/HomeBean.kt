package com.cg.xqkj.cportal.main.bean

/**
 *  author : chenguo
 *  date : 2020/5/13
 *  description : {  }
 */
class HomeBean {
    var banners:ArrayList<String>? = null
    var centerBanner:String? = null
    var list:ArrayList<InnerItem>? = null

    class InnerItem{
        var type:Int? = 0
        var title:String? = null
        var list:ArrayList<StoryBean>? = null
    }
}