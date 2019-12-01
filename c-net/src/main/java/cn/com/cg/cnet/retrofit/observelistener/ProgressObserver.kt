package cn.com.cg.cnet.retrofit.observelistener

import android.content.Context
import cn.com.cg.cnet.retrofit.base.response.BaseResponse
import cn.com.cg.cnet.retrofit.bean.ErrorBean
import cn.com.cg.cnet.retrofit.exception.ErrorType
import cn.com.cg.cnet.retrofit.utils.NetLoadingDialog
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/25 15:09
 */
abstract class ProgressObserver<T>(
    private val context: Context
) : Observer<Any> {
    private val TAG = "ProgressObserver--->"

    abstract fun success(data: T)
    abstract fun failure(code: Int, msg: String)

    private object Status {
        const val SUCCESS = 200
    }

    override fun onSubscribe(d: Disposable) {
        NetLoadingDialog.show(context)
    }

    override fun onNext(t: Any) {
        try {
            var response = t as BaseResponse<T>
            if (response.code == Status.SUCCESS) {
                try {
                    success(response.data)
                }catch (e:Exception){
                    onError(e)
                }
                return
            }
            val errorBean: ErrorBean = when (response.code) {
                ErrorType.INTERNAL_SERVER_ERROR.code ->
                    ErrorType.INTERNAL_SERVER_ERROR.getApiErrorModel(context)
                ErrorType.BAD_GATEWAY.code ->
                    ErrorType.BAD_GATEWAY.getApiErrorModel(context)
                ErrorType.NOT_FOUND.code ->
                    ErrorType.NOT_FOUND.getApiErrorModel(context)
                else -> ErrorBean(response.code, response.msg)
            }
            failure(response.code, errorBean.message)
            return
        }catch (e:Exception){
            onError(e)
        }
    }

    override fun onComplete() {
        NetLoadingDialog.cancel()
    }

    override fun onError(e: Throwable) {
        NetLoadingDialog.cancel()
        val errorType: ErrorType = when (e) {
            is UnknownHostException -> ErrorType.NETWORK_NOT_CONNECT
            is ConnectException -> ErrorType.NETWORK_NOT_CONNECT
            is SocketTimeoutException -> ErrorType.CONNECTION_TIMEOUT
            else -> ErrorType.UNEXPECTED_ERROR
        }
        failure(errorType.code, errorType.getApiErrorModel(context).message)
    }
}