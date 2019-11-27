package cn.com.cg.router.annotation

import kotlin.annotation.Retention
/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/28 10:08
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CMethod(val path: String){

}