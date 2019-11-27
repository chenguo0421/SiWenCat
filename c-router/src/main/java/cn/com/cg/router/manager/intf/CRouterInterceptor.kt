package cn.com.cg.router.manager.intf

import cn.com.cg.router.manager.params.RouterParamsManager

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/9/21 10:36
 */
interface CRouterInterceptor {
    open fun onInterceptor(paramsManager: RouterParamsManager)
}