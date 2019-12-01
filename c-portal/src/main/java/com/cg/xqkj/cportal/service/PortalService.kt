package com.cg.xqkj.cportal.service

import cn.com.cg.cnet.retrofit.base.response.BaseResponse
import com.cg.xqkj.cportal.login.bean.RequestLoginBean
import com.cg.xqkj.cportal.login.bean.ResponseLoginBean
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  author : chenguo
 *  date : 2019/12/1 14:15
 *  description : { 请添加该类的描述 }
 */
interface PortalService {

    @POST("/siwen/login")
    fun login(@Body request:RequestLoginBean): Observable<BaseResponse<ResponseLoginBean>> //括号中请求头可为空

}