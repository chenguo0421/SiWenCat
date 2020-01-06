package cn.com.cg.ccommon.utils

class StringUtils {
    companion object{
        fun isEmpty(str:String?):Boolean{
            return str == null || str.isEmpty()
        }
    }
}