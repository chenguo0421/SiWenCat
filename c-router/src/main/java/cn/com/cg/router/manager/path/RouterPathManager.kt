package cn.com.cg.router.manager.path

import android.content.Context
import cn.com.cg.router.bean.RouterBean
import cn.com.cg.router.utils.RouterXmlParser

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/27 20:25
 */
class RouterPathManager {

    /**
     * 类路由表，所有类路由可来这里查询
     */
    var routerMap: HashMap<String, RouterBean>? = null

    /**
     * 方法路由表，所有方法路由可来这里查询
     */
    var methodMap: HashMap<String, String>? = null

    var clsPathLists:ArrayList<String>? = null

    private constructor()

    fun init(context:Context){
        if (routerMap == null) {
            routerMap = HashMap()
        }
        if (methodMap == null) {
            methodMap = HashMap()
        }
        if (clsPathLists == null) {
            clsPathLists = ArrayList()
        }
        RouterXmlParser.parseRouterMap(context,routerMap,methodMap,clsPathLists)
    }

    /**
     * 判断当前类是否是带有注解的类
     */
    fun isAnnotationClass(clsPath:String):Boolean{
        for (item in clsPathLists!!){
            if (clsPath == item){
                return true
            }
        }
        return false
    }


    /**
     * 通过CRouter注解值查询所在类路径
     */
    fun findClassFromRouterPath(context:Context,routerPath: String):  Class<*>? {
        if (routerMap == null) {
            init(context)
        }
        var clsPath = routerMap?.get(routerPath)?.classPaths
        try {
            return Class.forName(clsPath!!)
        }catch (e:Exception){}
        return null
    }


    /**
     * 通过CMethod注解值查询所在类的路径
     */
    fun findClassPathByMethodPath(context: Context, action: String): String? {
        if (methodMap == null) {
            init(context)
        }
        return methodMap?.get(action)
    }

    /**
     * 通过FragmentTag查找Fragment实体类
     */
    fun findRealPathByTag(clzPath: String?, tag: String?): String? {
        val list = clzPath?.let { RouterBeanManager.getInstance().getFragmentTagsByClassPath(it) }
        var isContain = false
        if (list != null && list.size > 0) {
            for (item in list){
                if(tag.equals(item)){
                    isContain = true
                    break
                }
            }
        }

        return if (isContain){
            clzPath + tag
        }else{
            clzPath
        }
    }



    companion object {
        @Volatile
        private var instance: RouterPathManager? = null
        fun getInstance(): RouterPathManager {
            if (instance == null) {
                synchronized(RouterPathManager::class.java){
                    if (instance == null) {
                        instance = RouterPathManager()
                    }
                }
            }
            return instance!!
        }

    }
}


