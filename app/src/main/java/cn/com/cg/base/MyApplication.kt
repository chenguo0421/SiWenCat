package cn.com.cg.base

import android.annotation.SuppressLint
import android.content.Context
import cn.com.cg.ccommon.crash.CrashHandler
import cn.com.cg.ccommon.utils.Constants
import cn.com.cg.ccommon.utils.SharepreferenceUtils
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.clog.CLog
import cn.com.cg.router.annotation.CMethod
import com.github.moduth.blockcanary.BlockCanary




/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/28 14:47
 */
class MyApplication : CRouterApplication() {

    override fun onCreate() {
        super.onCreate()
//        Logger.init(this)
        CLog.init(this)
        ToastUtils.init(this)
        CrashHandler.instance.init(this, true)
        // 在主进程初始化调用哈
        BlockCanary.install(this, AppBlockCanaryContext()).start()
    }
}