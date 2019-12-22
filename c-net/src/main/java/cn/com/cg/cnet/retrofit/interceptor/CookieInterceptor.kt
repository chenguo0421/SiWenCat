package cn.com.cg.cnet.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import cn.com.cg.cnet.retrofit.utils.CookieManager


/**
 *  author : chenguo
 *  date : 2019/12/22 20:10
 *  description : { 请添加该类的描述 }
 */
class CookieInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        var originalResponse: Response?
        originalResponse = if (CookieManager.cookies.isNotEmpty()){
            for (cookie: String in CookieManager.cookies) {
                builder.addHeader("Cookie", cookie)
            }
            chain.proceed(builder.build())
        }else{
            chain.proceed(chain.request())
        }

        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val cookies = HashSet<String>()

            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            CookieManager.cookies = cookies
        }
        return originalResponse
    }
}