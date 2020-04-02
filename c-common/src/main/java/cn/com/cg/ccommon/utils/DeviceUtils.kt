package cn.com.cg.ccommon.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


class DeviceUtils {

    companion object{
        fun getScreenWidth(context: Context): Int {
            val outMetrics = DisplayMetrics()
            getWindowManager(context).defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

        fun getScreenHeight(context: Context): Int {
            val outMetrics = DisplayMetrics()
            getWindowManager(context).defaultDisplay.getMetrics(outMetrics)
            return outMetrics.heightPixels
        }

        fun getRealScreenWidth(context: Context): Int {
            val outMetrics = DisplayMetrics()
            getWindowManager(context).defaultDisplay.getRealMetrics(outMetrics)
            return outMetrics.widthPixels
        }

        fun getRealScreenHeight(context: Context): Int {
            val outMetrics = DisplayMetrics()
            getWindowManager(context).defaultDisplay.getRealMetrics(outMetrics)
            return outMetrics.heightPixels
        }

        private fun getWindowManager(context: Context):WindowManager{
            return context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
    }

}