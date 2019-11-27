package cn.com.cg.router.manager.interceptor

import cn.com.cg.router.manager.intf.CRouterInterceptor
import cn.com.cg.router.manager.params.RouterParamsManager

/**
 * Discription  { 路由跳转拦截 }
 * author  chenguo7
 * Date  2019/11/25 11:35
 */
class DefaultRouterInterceptor : CRouterInterceptor {

    override fun onInterceptor(paramsManager: RouterParamsManager) {
        //默认拦截器拦截跳转功能测试
//        if("Test2Activity" == paramsManager.getAction()){
//            paramsManager.setAction("TestFMActivity")
//        }
    }
}