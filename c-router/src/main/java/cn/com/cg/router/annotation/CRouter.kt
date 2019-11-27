package cn.com.cg.router.annotation

import kotlin.annotation.Retention

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/28 10:07
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class CRouter(val path: String) {}
