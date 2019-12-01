package cn.com.cg.router.manager.method

import cn.com.cg.base.BaseActivity
import cn.com.cg.base.BaseDialogFragment
import cn.com.cg.base.BaseFragment
import cn.com.cg.router.annotation.CMethod
import cn.com.cg.router.manager.path.RouterBeanManager
import cn.com.cg.router.manager.path.RouterPathManager
import java.lang.reflect.Method

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/30 15:27
 */
class RouterMethodManager {



    fun invoke(clzPath: String?,tag:String?, action: String?,vararg params:Any): Any? {
        val instance = Class.forName(clzPath!!).newInstance()
        if(instance is BaseActivity<*,*>){
            if (getInvokeActivityMaps(clzPath).size > 0){
                return invokeMethod(getInvokeActivityMaps(clzPath), RouterBeanManager.getInstance().getActBean(clzPath!!), action, params)
            }
        }else if(instance is BaseFragment<*,*>){
            val realClsPath = RouterPathManager.getInstance().findRealPathByTag(clzPath,tag)
            val bean = RouterBeanManager.getInstance().getFMBean(realClsPath!!)
            if (bean != null) {
                if(getInvokeFragmentMaps(clzPath).size > 0){
                    return invokeMethod(getInvokeFragmentMaps(clzPath), bean, action, params)
                }
            }

        }else if(instance is BaseDialogFragment<*,*>){
            val realClsPath = RouterPathManager.getInstance().findRealPathByTag(clzPath,tag)
            val bean = RouterBeanManager.getInstance().getFMBean(realClsPath!!)
            if (bean != null) {
                if(getInvokeFragmentMaps(clzPath).size > 0){
                    return invokeMethod(getInvokeFragmentMaps(clzPath), bean, action, params)
                }
            }

        }else {
            if (getInvokeOthersMaps(clzPath).size > 0) {
                return invokeMethod(getInvokeOthersMaps(clzPath), RouterBeanManager.getInstance().getOtherBean(clzPath!!), action, params
                )
            }
        }
        return null
    }

    private fun invokeMethod(tempMap:HashMap<String,Method>, cls: Any?, action: String?, vararg params:Any): Any? {
        if (tempMap[action] != null) {
            return tempMap[action]?.invoke(cls,*params)
        }
        return null
    }


    private fun getInvokeOthersMaps(clzPath: String?):HashMap<String,Method> {
        var tempMap:HashMap<String,Method> = HashMap()
        try {
            val cls = Class.forName(clzPath!!)
            val methods = cls.declaredMethods
            for (method in methods) {
                val cMethod = method.getAnnotation(CMethod::class.java)
                if (cMethod != null) {
                    tempMap[cMethod.path] = method
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return tempMap
    }

    private fun getInvokeFragmentMaps(clzPath: String?): HashMap<String,Method> {
        val tempMap:HashMap<String,Method> = HashMap()
        try {
            var cls = Class.forName(clzPath!!)
            val methods = cls.declaredMethods
            for (method in methods) {
                val cMethod = method.getAnnotation(CMethod::class.java)
                if (cMethod != null) {
                    tempMap[cMethod.path] = method
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
      return tempMap
    }

    private fun getInvokeActivityMaps(clzPath: String?): HashMap<String,Method> {
        var bean = RouterBeanManager.getInstance().getActBean(clzPath!!)
        val tempMap:HashMap<String,Method> = HashMap()
        if (bean != null) {
            try {
                var cls = Class.forName(clzPath)
                val methods = cls.declaredMethods
                for (method in methods) {
                    val cMethod = method.getAnnotation(CMethod::class.java)
                    if (cMethod != null) {
                        tempMap[cMethod.path] = method
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        return tempMap
    }


    /**
     * 单例
     */
    companion object {
        @Volatile
        private var instance: RouterMethodManager? = null
        fun getInstance(): RouterMethodManager {
            if (instance == null) {
                synchronized(RouterMethodManager::class.java){
                    if (instance == null) {
                        instance = RouterMethodManager()
                    }
                }
            }
            return instance!!
        }

    }
}