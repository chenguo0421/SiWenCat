package cn.com.cg.clog.utils

import android.app.Activity
import cn.com.cg.clog.CLog
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.*

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/9/16 20:00
 */
class CheckLeak {

    private var mList: MutableList<Int> = Collections.synchronizedList(ArrayList())
    private var mMap = WeakHashMap<Activity, Int>()
    private var mQueue = ReferenceQueue<Object>()
    private var mPhantomReference = WeakReference(Object(), mQueue)

    internal fun add(activity: Activity) {
        val code = activity.hashCode()
        mList.add(code)
        mMap[activity] = code
    }

    internal fun remove(activity: Activity) {
        mList.remove(Integer.valueOf(activity.hashCode()))
    }

    @Throws(InterruptedException::class)
    internal fun checkLeak(): String? {
        if (!mPhantomReference.isEnqueued){
            return null
        }
        val stringBuilder = StringBuilder()
        for (activity in mMap.keys) {
            val s = activity.hashCode()
            val name = activity.javaClass.name
            if (!mList.contains(s)) {
                stringBuilder.append(name).append(";")
                CLog.e("$name 可能发生内存泄漏,请检查")
            }
        }
        mQueue.remove()
        mPhantomReference = WeakReference(Object(), mQueue)
        return stringBuilder.toString()
    }
}