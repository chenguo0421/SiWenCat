package com.cg.xqkj.cportal.intf

/**
 *  author : chenguo
 *  date : 2020/8/12 22:17
 *  description : { 请添加该类的描述 }
 */
interface OnItemClickListener<T> {
    fun onItemClick(item:T,position:Int)
}