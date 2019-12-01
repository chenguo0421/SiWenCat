package cn.com.cg.cnet.retrofit.base.response


/**
 *  author : chenguo
 *  date : 2019/12/1 14:11
 *  description : { 返回实体Bean }
 */
data class BaseResponse<T>(var code: Int, var data: T, var msg: String)  {
}