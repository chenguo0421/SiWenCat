package cn.com.cg.router.manager.path

import androidx.fragment.app.Fragment
import cn.com.cg.base.BaseActivity
import cn.com.cg.base.BaseDialogFragment
import cn.com.cg.base.BaseFragment

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/30 14:54
 */
class RouterBeanManager{

    /**
     * Activity实例
     */
    private var actMap:HashMap<String,BaseActivity<*,*>>? = null

    /**
     * tag用于区分Activity的不同实例
     */
    private var actTagsMap:HashMap<String,ArrayList<String>>? = null

    /**
     * Fragment实例
     */
    private var fmMap:HashMap<String,Fragment>? = null

    /**
     * tag用于区分Fragment的不同实例
     */
    private var fmTagsMap:HashMap<String,ArrayList<String>>? = null

    /**
     * 其他工具类实例
     */
    private var utilMap: HashMap<String, Any?>? = null


    fun registerAct(obj: BaseActivity<*,*>) {
        if (RouterPathManager.getInstance().isAnnotationClass(obj::class.qualifiedName!!)){
            actMap?.put(obj::class.qualifiedName!!,obj)
        }
    }

    fun registerFM(obj: Fragment) {
        if (RouterPathManager.getInstance().isAnnotationClass(obj::class.qualifiedName!!)){
            if (obj is BaseFragment<*,*>){
                fmMap?.put(obj::class.qualifiedName!! + obj.fragmentTag,obj)
                var list = fmTagsMap?.get(obj::class.qualifiedName)
                if (list == null) {
                    list = ArrayList()
                }
                obj.fragmentTag?.let { list!!.add(it) }
                fmTagsMap?.put(obj::class.qualifiedName!!,list)
            }else if (obj is BaseDialogFragment<*,*>){
                fmMap?.put(obj::class.qualifiedName!! + obj.fragmentTag,obj)
                var list = fmTagsMap?.get(obj::class.qualifiedName)
                if (list == null) {
                    list = ArrayList()
                }
                obj.fragmentTag?.let { list.add(it) }
                fmTagsMap?.put(obj::class.qualifiedName!!,list)
            }
        }
    }

    fun unRegisterAct(obj: BaseActivity<*,*>) {
        actMap?.remove(obj::class.qualifiedName!!)
    }

    fun unRegisterFM(obj: Fragment) {
        if (obj is BaseFragment<*,*>){
            fmMap?.remove(obj::class.qualifiedName!! + obj.fragmentTag)
        }else if (obj is BaseDialogFragment<*,*>){
            fmMap?.remove(obj::class.qualifiedName!! + obj.fragmentTag)
        }
        fmTagsMap?.remove(obj::class.qualifiedName!!)
    }

    fun getActBean(clsPath:String):BaseActivity<*,*>?{
        return actMap!![clsPath]
    }

    fun getFMBean(clsPath:String):Fragment?{
        return fmMap!![clsPath]
    }

    fun getOtherBean(clzPath: String): Any? {
        if (utilMap!![clzPath]==null){
            utilMap!![clzPath] = Class.forName(clzPath).newInstance()
        }
        return utilMap!![clzPath]
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