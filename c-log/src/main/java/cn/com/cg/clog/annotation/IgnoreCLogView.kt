package cn.com.cg.clog.annotation

/**
 * Discription  { 支持指定注解页面不显示日志 }
 * author  chenguo7
 * Date  2019/9/16 16:50
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
public annotation class IgnoreCLogView(){

}