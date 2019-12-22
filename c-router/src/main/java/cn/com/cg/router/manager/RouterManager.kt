package cn.com.cg.router.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import cn.com.cg.base.BaseActivity
import cn.com.cg.base.BaseDialogFragment
import cn.com.cg.base.BaseFragment
import cn.com.cg.clog.CLog
import cn.com.cg.router.manager.callback.RouterCallBackManager
import cn.com.cg.router.manager.intf.CRouterInterceptor
import cn.com.cg.router.manager.intf.RouterCallBack
import cn.com.cg.router.manager.method.RouterMethodManager
import cn.com.cg.router.manager.params.RouterParamsManager
import cn.com.cg.router.manager.path.RouterPathManager

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/27 18:15
 */
class RouterManager private constructor(){

    val TAG : String? = "RouterManager"
    /**
     *单例
     */
    companion object {

        private @Volatile var instance: RouterManager? = null
        fun getInstance(): RouterManager{
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = RouterManager()
                    }
                }
            }
            return instance!!
        }
    }


    /**
     * 初始化静态路由表到内存
     */
    fun init(applicationContext: Context) {
        RouterPathManager.getInstance().init(applicationContext)
    }


    /**
     * FragmentTag,可以通过此tag来区分同一个类的多个对象
     */
    fun fragmentTag(tag: String): RouterManager {
        RouterParamsManager.getInstance().setFragmentTag(tag)
        return getInstance()
    }




    /**
     * 共享元素
     */
    fun sharedElement(view:View): RouterManager{
        RouterParamsManager.getInstance().setSharedElement(view)
        return getInstance()
    }


    /**
     * 设置页面切换动画
     */
    fun anim(enterAnim:Int,outerAnim:Int): RouterManager{
        RouterParamsManager.getInstance().setAnim(enterAnim,outerAnim)
        return getInstance()
    }


    /**
     * 配置Context
     */
    fun with(context: Context): RouterManager {
        RouterParamsManager.getInstance().setContext(context)
        return getInstance()

    }


    /**
     * 自定义Intent，启动方式由使用者去设置
     */
    fun intent(intent:Intent): RouterManager{
        RouterParamsManager.getInstance().setIntent(intent)
        return getInstance()
    }


    /**
     * 配置路由
     */
    fun action(action: String): RouterManager {
        RouterParamsManager.getInstance().setAction(action)
        return getInstance()
    }



    /**
     * 请求方设置回调
     */
    fun setCallBack(callBack:RouterCallBack): RouterManager {
        RouterParamsManager.getInstance().setCallBack(callBack)
        return getInstance()
    }


    /**
     * 设置拦截器
     */
    fun setInterceptor(interceptor: CRouterInterceptor):RouterManager{
        RouterParamsManager.getInstance().setInterceptor(interceptor)
        return getInstance()
    }


    /**
     * 目标方调用回调
     */
    fun onCallBack(callBackID:String?,data:Any){
        if (callBackID == null) {
            return
        }
        val callBack = RouterCallBackManager.getInstance().get(callBackID)
        callBack?.onCallBack(data)
        //完成一次回调，清除脏数据
        clearCatchData()
    }

    /**
     * 路由跳转Activity或获得Fragment实例
     */
    fun navigation():Any? {
        CLog.d("RouterManager navigation with action ${RouterParamsManager.getInstance().getAction()}")
        if (RouterParamsManager.getInstance().getInterceptor() != null) {//有拦截器的话
            RouterParamsManager.getInstance().getInterceptor()?.onInterceptor(RouterParamsManager.getInstance())
        }

        if (RouterParamsManager.getInstance().getAction() != null && RouterParamsManager.getInstance().getContext() != null) {
            val cls = RouterPathManager.getInstance()
                .findClassFromRouterPath(RouterParamsManager.getInstance().getContext()!!, RouterParamsManager.getInstance().getAction()!!)!!.newInstance()
            if (cls is BaseActivity<*, *>) {
                val intent = createIntent()
                jumpActivity(RouterParamsManager.getInstance().getContext()!!, intent)
                //发生一次请求，清除脏数据
                clearCatchData()
                return null
            } else if (cls is BaseFragment<*, *>) {
                val instance = cls.getInstance()
                instance.fragmentTag = RouterParamsManager.getInstance().getFragmentTag()
                clearCatchData()
                return instance
            } else if (cls is BaseDialogFragment<*,*>){
                val instance = cls.getInstance()
                instance.fragmentTag = RouterParamsManager.getInstance().getFragmentTag()
                clearCatchData()
                return instance
            }
        }
        return null
    }

    /**
     * 清理用完的脏数据
     */
    private fun clearCatchData() {
        RouterParamsManager.getInstance().clearCatchData()
    }

    /**
     * 调用指定的注解方法
     */
    fun callMethod(vararg params: Any): Any? {
        if (RouterParamsManager.getInstance().getAction() == null) {
            throw Exception("please with action first!")
        }
        CLog.d("RouterManager callMethod with action ${RouterParamsManager.getInstance().getAction()}")
        val clsPath: String? = RouterPathManager.getInstance()
            .findClassPathByMethodPath(RouterParamsManager.getInstance().getContext()!!, RouterParamsManager.getInstance().getAction()!!)
        if (clsPath != null) {
            return RouterMethodManager.getInstance().invoke(clsPath,RouterParamsManager.getInstance().getFragmentTag(),RouterParamsManager.getInstance().getAction(), *params)
        }
        return null
    }

    /**
     * 页面切换
     */
    private fun jumpActivity(context: Context, intent: Intent) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP && context is Activity && RouterParamsManager.getInstance().getSharedElement() != null) {
            val compat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context
                    , RouterParamsManager.getInstance().getSharedElement()!!
                    , RouterParamsManager.getInstance().getSharedElement()!!.transitionName!!)
            ActivityCompat.startActivity(context, intent, compat.toBundle())
            CLog.d("RouterManager jumpActivity with shareElements")
        } else {
            context.startActivity(intent)
            if (context is Activity && RouterParamsManager.getInstance().getAnim()?.size == 2) {
                RouterParamsManager.getInstance().getAnim()?.get(1)?.let {
                    RouterParamsManager.getInstance().getAnim()?.get(0)?.let { it1 ->
                        context.overridePendingTransition(
                            it1,
                            it
                        )
                    }
                }
            }
            CLog.d("RouterManager jumpActivity ordinary")
        }
    }

    /**
     * 构建构建Intent
     */
    private fun createIntent(): Intent {
        if (RouterParamsManager.getInstance().getContext() == null){
            throw Exception("please with context first!")
        }
        if (RouterParamsManager.getInstance().getAction() == null){
            throw Exception("please with action first!")
        }
        if (RouterParamsManager.getInstance().getIntent() == null){
            RouterParamsManager.getInstance().setIntent(Intent())
        }

        val clz: Class<*>? = RouterPathManager.getInstance().findClassFromRouterPath(RouterParamsManager.getInstance().getContext()!!, RouterParamsManager.getInstance().getAction()!!)
        RouterParamsManager.getInstance().getIntent()?.setClass(RouterParamsManager.getInstance().getContext()!!, clz!!)
        if (RouterParamsManager.getInstance().getCallBack() != null) {
            RouterParamsManager.getInstance().getIntent()?.putExtra(RouterParamsManager.METHODCALLBACKID, RouterParamsManager.getInstance().getCallBack())
        }
        return RouterParamsManager.getInstance().getIntent()!!
    }


    fun finish() {
        if (RouterParamsManager.getInstance().getContext() == null){
            throw Exception("please with context first!")
        }
        if (RouterParamsManager.getInstance().getContext() is Activity){
            if (RouterParamsManager.getInstance().getAnim()?.size == 2) {
                (RouterParamsManager.getInstance().getContext() as Activity).finish()
                RouterParamsManager.getInstance().getAnim()?.get(1)?.let {
                    RouterParamsManager.getInstance().getAnim()?.get(0)?.let { it1 ->
                        (RouterParamsManager.getInstance().getContext() as Activity).overridePendingTransition(
                            it1,
                            it
                        )
                    }
                }
                CLog.d("RouterManager finish with anim")
            }else{
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                    (RouterParamsManager.getInstance().getContext() as Activity).finishAfterTransition()
                    CLog.d("RouterManager finish with ShareElements")
                }else{
                    (RouterParamsManager.getInstance().getContext() as Activity).finish()
                    CLog.d("RouterManager finish with ordinary")
                }
            }
        }
    }



}
