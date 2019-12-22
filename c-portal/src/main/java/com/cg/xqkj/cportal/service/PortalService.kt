package com.cg.xqkj.cportal.service

import cn.com.cg.cnet.retrofit.base.response.BaseResponse
import com.cg.xqkj.cportal.login.bean.RequestLoginBean
import com.cg.xqkj.cportal.login.bean.ResponseLoginBean
import com.cg.xqkj.cportal.register.bean.*
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
    fun login(@Body request:RequestLoginBean): Observable<BaseResponse<ResponseLoginBean>>


    @POST("/siwen/register/getPhoneToken")
    fun phoneToken(@Body request:RequestPhoneTokenBean): Observable<BaseResponse<ResponsePhoneTokenBean>>


    @POST("/siwen/register/checkPhone")
    fun register(@Body request:RequestRegisterBean): Observable<BaseResponse<Any>>


    @POST("/siwen/register/psw")
    fun submitPSW(@Body request: RequestRegisterPSWBean): Observable<BaseResponse<ResponseRegisterPSWBean>>


}