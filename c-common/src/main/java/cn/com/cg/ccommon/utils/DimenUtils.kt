package cn.com.cg.ccommon.utils

import android.content.Context

object DimenUtils {
    fun dp2px(context: Context, dp:Float):Float =dp * context.resources.displayMetrics.density

    fun px2dp(context:Context,px:Float):Float =px / context.resources.displayMetrics.density

    fun sp2px(context: Context, sp:Float):Float = sp * context.resources.displayMetrics.scaledDensity

    fun px2sp(context: Context, px:Float):Float = px / context.resources.displayMetrics.scaledDensity

}