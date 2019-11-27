package cn.com.cg.router.utils

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import cn.com.cg.router.annotation.CRouter



/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/28 9:42
 */

class AnnotationUtils{

    companion object{

        @Volatile
        var TAG: String = "AnnotationUtils"


        fun getClassAnnotation(obj: Activity): String?{
            Class.forName("").annotations
            val anno = obj::class.java!!.getAnnotation(CRouter::class.java)
            if(anno != null){
                Log.e(TAG,"path = " + anno.path)
                return anno.path
            }
            return null
        }

        fun getClassAnnotation(obj: Fragment): String? {
            val anno = obj::class.java!!.getAnnotation(CRouter::class.java)
            if(anno != null){
                Log.e(TAG,"path = " + anno.path)
                return anno.path
            }
            return null
        }
    }


}