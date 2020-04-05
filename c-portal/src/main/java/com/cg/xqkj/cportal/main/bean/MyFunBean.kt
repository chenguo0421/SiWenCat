package com.cg.xqkj.cportal.main.bean

data class MyFunBean(
    val type:Int = -1,
    val resource:Int = 0,
    val name:String? = null,
    val contentMenu:ArrayList<MenuBean>? = null) {
}