package cn.com.cg.testmodule1.utils

import cn.com.cg.router.annotation.CMethod

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/9/2 16:51
 */
class MyUtils{


    @CMethod("/MyUtils/testCallUtilsMethod")
    fun testCallUtilsMethod(vararg params: Any): Any? {
        return params[0].toString() + " it is MyUtils testCallUtilsMethod return"
    }

}