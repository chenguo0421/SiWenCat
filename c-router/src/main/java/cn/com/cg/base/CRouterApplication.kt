package cn.com.cg.base

import android.app.Application
import cn.com.cg.router.manager.RouterManager
import cn.com.cg.router.manager.interceptor.DefaultRouterInterceptor

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/28 14:44
 */
open class CRouterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RouterManager.getInstance().setInterceptor(DefaultRouterInterceptor()).init(this)
    }

}