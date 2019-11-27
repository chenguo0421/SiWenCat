package cn.com.cg.base

import cn.com.cg.clog.CLog


/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/28 14:47
 */
class MyApplication : CRouterApplication() {
    override fun onCreate() {
        super.onCreate()
//        Logger.init(this)
        CLog.init(this)
    }
}