package cn.com.cg.cnet.retrofit.observelistener

import cn.com.cg.cnet.retrofit.exception.ExceptionHandler


/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/25 15:09
 */
public interface ObserveResponseListener {
    /**
     * 响应成功
     * @param t
     */
    abstract fun onNext(t: Any)

    /**
     * 响应失败
     * @param throwable
     */
    abstract fun onError(throwable: ExceptionHandler.ResponseThrowable)
}