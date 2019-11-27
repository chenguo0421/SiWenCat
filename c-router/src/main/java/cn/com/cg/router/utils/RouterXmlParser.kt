package cn.com.cg.router.utils

import android.content.Context
import android.util.Xml
import cn.com.cg.router.bean.RouterBean
import org.xmlpull.v1.XmlPullParser
import java.io.IOException
import java.io.InputStream
import java.util.HashMap
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/8/29 19:34
 */
open class RouterXmlParser{

    companion object{

        open fun parseRouterMap(context: Context,routerMap: HashMap<String, RouterBean>?,methodMap: HashMap<String, String>?,clsPathLists:ArrayList<String>?){
            routerMap?.clear()
            methodMap?.clear()
            clsPathLists?.clear()
            pull(context, "CRouter.xml", routerMap, methodMap,clsPathLists)
        }



        /**
         * 解析assets路径下的router.xml
         *
         * @param context
         * @param fileName
         * @return
         */
        private fun pull(context: Context, fileName: String,map: HashMap<String, RouterBean>?,methodMap:HashMap<String, String>?,clsPathLists:ArrayList<String>?){
            //获得一个pull解析器
            val parser = Xml.newPullParser()
            var router: RouterBean? = null
            var cMethodIds:ArrayList<String>? = null
            var clsPath: String? = null
            var methodPath: String? = null
            var ins: InputStream? = null
            try {
                ins = context.resources.assets.open(fileName)
                //添加文档信息进pull解析器
                parser.setInput(ins, "UTF-8")
                //得到节点信息
                var evtType = parser.eventType
                while (evtType != XmlPullParser.END_DOCUMENT) {//如果不是文档结束节点
                    //文档的开始
                    when (evtType) {
                        XmlPullParser.START_TAG       //开始标签
                        -> {
                            val tag = parser.name
                            if ("CRouter" == tag) {
                                router = RouterBean()
                            }

                            if (router != null) {
                                if ("RouterPath" == tag) {
                                    router!!.routerPath = parser.nextText()
                                } else if ("ClassPath" == tag) {
                                    val path =  parser.nextText()
                                    router!!.classPaths = path
                                    clsPath = path
                                    clsPathLists?.add(path)
                                } else if ("MethodPath" == tag){
                                    cMethodIds = ArrayList()
                                } else if ("MethodPathItem" == tag){
                                    val path =  parser.nextText()
                                    cMethodIds?.add(path)
                                    methodPath = path
                                    methodMap?.put(methodPath, clsPath!!)
                                }
                            }
                        }
                        XmlPullParser.END_TAG       //结束标签
                        -> {
                            if ("CRouter" == parser.name && router != null) {
                                router.methodIDs = cMethodIds!!
                                try {
                                    map!![router.routerPath] = router
                                }catch (e:Exception){}
                                router = null
                            }
                        }
                    }
                    evtType = parser.next()
                }
            } catch (e:Exception) {
                e.printStackTrace()
            } finally {
                try {
                    ins!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

}