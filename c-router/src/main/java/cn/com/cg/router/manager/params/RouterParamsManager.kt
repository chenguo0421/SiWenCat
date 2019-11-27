package cn.com.cg.router.manager.params

import android.content.Context
import android.content.Intent
import android.view.View
import cn.com.cg.router.manager.callback.RouterCallBackManager
import cn.com.cg.router.manager.intf.CRouterInterceptor
import cn.com.cg.router.manager.intf.RouterCallBack
import java.lang.ref.SoftReference

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/9/3 19:13
 */
open class RouterParamsManager {

    private var intent: Intent? = null
    private var action: String? = null
    private var callBackID: String? = null
    private var clsName: String? = null
    private var tag: String? = null
    private var animArr: IntArray? = null
    private var view: SoftReference<View>? = null
    private var context: SoftReference<Context>? = null
    private var interceptor: CRouterInterceptor? = null



    companion object{

        var METHODCALLBACKID:String = "METHOD_CALLBACKID"

        private var instance: RouterParamsManager? = null

        fun getInstance(): RouterParamsManager{
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = RouterParamsManager()
                    }
                }
            }
            return instance!!
        }
    }


    fun setFragmentTag(tag:String){
        getInstance().tag = tag
    }


    fun getFragmentTag():String?{
        return getInstance().tag
    }


    fun setSharedElement(view: View) {
        getInstance().view = SoftReference(view)
    }

    fun getSharedElement():View?{
        return getInstance().view?.get()
    }

    fun setAnim(enterAnim: Int, outerAnim: Int) {
        getInstance().animArr = IntArray(2)
        getInstance().animArr?.set(0, enterAnim)
        getInstance().animArr?.set(1, outerAnim)
    }

    fun getAnim():IntArray? {
        return getInstance().animArr
    }

    fun setContext(context: Context) {
        getInstance().context = SoftReference(context)
    }

    fun getContext():Context? {
        return getInstance().context?.get()
    }

    fun setIntent(intent: Intent) {
        getInstance().intent = intent
    }

    fun getIntent():Intent?{
        return getInstance().intent
    }

    fun setAction(action: String) {
        getInstance().action = action
    }

    fun getAction():String?{
        return getInstance().action
    }

    fun setCallBack(callBack: RouterCallBack) {
        getInstance().callBackID = RouterCallBackManager.getInstance().put(callBack)
    }

    fun getCallBack():String?{
        return getInstance().callBackID
    }

    fun setInterceptor(interceptor: CRouterInterceptor) {
        getInstance().interceptor = interceptor
    }

    fun getInterceptor():CRouterInterceptor?{
        return getInstance().interceptor
    }

    fun clearCatchData() {
        getInstance().context = null
        getInstance().action = null
        getInstance().intent = null
        getInstance().callBackID = null
        getInstance().clsName = null
        getInstance().animArr = null
        getInstance().view = null
        getInstance().tag = null
//        RouterParamsManager.interceptor = null  //脏数据清除，拦截器不清除，可以覆盖拦截器
    }

}