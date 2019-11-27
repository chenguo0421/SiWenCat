package cn.com.cg.router.manager.callback

import cn.com.cg.router.manager.intf.RouterCallBack
import java.util.*
import kotlin.collections.HashMap

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/27 19:13
 */
class RouterCallBackManager{

    var callBackMap:HashMap<String,RouterCallBack>? = null
    private var transacation = 0

    fun put(callBack: RouterCallBack): String {
        var id = getRandomId()
        callBackMap?.put(id,callBack)
        return id
    }

    fun get(id:String):RouterCallBack?{
        return callBackMap?.remove(id)
    }

    var TAG: String = "RouterCallBackManager"

    private constructor(){
        if (callBackMap == null) {
            callBackMap = HashMap()
        }
    }

    //生成唯一识别号 随机大写字母+年份后两位+月份+小时+分+4位随机数
    fun getRandomId(): String {
        var first = (Random().nextInt(26) + 65).toChar().toString()
        var second = (Random().nextInt(26) + 65).toChar().toString()
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minute = calendar.get(Calendar.MINUTE)

        if (transacation in (0..9998)) {
            transacation += 1
        } else if (transacation == 9999) {
            transacation = 0
        }
        var trans = String.format("%0" + 4 + "d", transacation)
        var mon = String.format("%0" + 2 + "d", month)
        var days = String.format("%0" + 2 + "d", day)
        var hou = String.format("%0" + 2 + "d", hour)
        var min = String.format("%0" + 2 + "d", minute)

        var str = StringBuffer("")
        str.append(first).append(second).append(year % 100).append(mon).append(days).append(hou).append(min).append(trans)

        return str.toString()
    }


    companion object {
        @Volatile
        private var instance: RouterCallBackManager? = null
        fun getInstance(): RouterCallBackManager {
            if (instance == null) {
                synchronized(RouterCallBackManager::class.java){
                    if (instance == null) {
                        instance = RouterCallBackManager()
                    }
                }
            }
            return instance!!
        }

    }
}


