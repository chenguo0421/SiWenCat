package cn.com.cg.ccommon.utils

import java.util.regex.Pattern

/**
 *  author : chenguo
 *  date : 2019/12/6 21:06
 *  description : { 正则表达式匹配 }
 */
class RegexUtils {
    companion object{

        /***
         * 手机号码检测
         */
        fun checkPhoneNum(num: String): Boolean{
            val regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5-9])|(166)|(19[8,9])|)\\d{8}$"
            val p = Pattern.compile(regExp)
            val m = p.matcher(num)
            return m.matches()
        }

    }
}