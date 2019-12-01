package com.cg.xqkj.cportal.login.bean

import java.io.Serializable

/**
 *  author : chenguo
 *  date : 2019/12/1 13:43
 *  description : { 请添加该类的描述 }
 */
class ResponseLoginBean:Serializable {
    lateinit var id:String
    lateinit var name:String
    var arg:Int = 0
    lateinit var remark:String
}