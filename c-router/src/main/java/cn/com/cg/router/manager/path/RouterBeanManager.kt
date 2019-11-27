package cn.com.cg.router.manager.path

import cn.com.cg.base.BaseActivity
import cn.com.cg.base.BaseFragment
import java.lang.ref.SoftReference

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/30 14:54
 */
class RouterBeanManager{

    /**
     * Activity实例
     */
    private var actMap:HashMap<String,SoftReference<BaseActivity<*,*>>>? = null

    /**
     * tag用于区分Activity的不同实例
     */
    private var actTagsMap:HashMap<String,ArrayList<String>>? = null

    /**
     * Fragment实例
     */
    private var fmMap:HashMap<String,SoftReference<BaseFragment<*,*>>>? = null

    /**
     * tag用于区分Fragment的不同实例
     */
    private var fmTagsMap:HashMap<String,ArrayList<String>>? = null

    /**
     * 其他工具类实例
     */
    private var utilMap: HashMap<String, SoftReference<Any?>>? = null


    fun registerAct(obj: BaseActivity<*,*>) {
        if (RouterPathManager.getInstance().isAnnotationClass(obj::class.qualifiedName!!)){
            actMap?.put(obj::class.qualifiedName!!,SoftReference(obj))
        }
    }

    fun registerFM(obj: BaseFragment<*,*>) {
        if (RouterPathManager.getInstance().isAnnotationClass(obj::class.qualifiedName!!)){
            fmMap?.put(obj::class.qualifiedName!! + obj.fragmentTag,SoftReference(obj))
            var list = fmTagsMap?.get(obj::class.qualifiedName)
            if (list == null) {
                list = ArrayList()
            }
            obj.fragmentTag?.let { list.add(it) }
            fmTagsMap?.put(obj::class.qualifiedName!!,list)
        }
    }

    fun unRegisterAct(obj: BaseActivity<*,*>) {
        actMap?.remove(obj::class.qualifiedName!!)
    }

    fun unRegisterFM(obj: BaseFragment<*,*>) {
        fmMap?.remove(obj::class.qualifiedName!! + obj.fragmentTag)
        fmTagsMap?.remove(obj::class.qualifiedName!!)
    }

    fun getActBean(clsPath:String):BaseActivity<*,*>?{
        return actMap!![clsPath]?.get()
    }

    fun getFMBean(clsPath:String):BaseFragment<*,*>?{
        return fmMap!![clsPath]?.get()
    }

    fun getOtherBean(clzPath: String): Any? {
        if (utilMap!![clzPath]==null){
            utilMap!![clzPath] = SoftReference(Class.forName(clzPath).newInstance())
        }
        return utilMap!![clzPath]?.get()
    }

    fun getFragmentTagsByClassPath(clsPaht:String):ArrayList<String>?{
        return fmTagsMap?.get(clsPaht)
    }


    private constructor(){
        if (actMap == null) {
            actMap = HashMap()
        }
        if (actTagsMap == null){
            actTagsMap = HashMap()
        }
        if (fmMap == null) {
            fmMap = HashMap()
        }
        if (fmTagsMap == null) {
            fmTagsMap = HashMap()
        }
        if (utilMap == null) {
            utilMap = HashMap()
        }
    }

    /**
     * 单例
     */
    companion object {
        @Volatile
        private var instance: RouterBeanManager? = null
        fun getInstance(): RouterBeanManager {
            if (instance == null) {
                synchronized(RouterPathManager::class.java){
                    if (instance == null) {
                        instance = RouterBeanManager()
                    }
                }
            }
            return instance!!
        }

    }
}