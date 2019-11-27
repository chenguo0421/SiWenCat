package cn.com.cg.cnet.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}