package cn.com.cg.cnet.retrofit.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import javax.net.ssl.SSLHandshakeException

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/25 15:11
 */
class ExceptionHandler {

    private val UNAUTHORIZED = 401
    private val FORBIDDEN = 403
    private val NOT_FOUND = 404
    private val REQUEST_TIMEOUT = 408
    private val INTERNAL_SERVER_ERROR = 500
    private val BAD_GATEWAY = 502
    private val SERVICE_UNAVAILABLE = 503
    private val GATEWAY_TIMEOUT = 504


    fun handleException(e: Throwable): ResponseThrowable {
        val throwable: ResponseThrowable
        if (e is HttpException) {
            throwable = ResponseThrowable(Error.HTTP_ERROR.type)
            when (e.code()) {
                UNAUTHORIZED, FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE, GATEWAY_TIMEOUT -> throwable.message =
                    "网络错误"//这里其实可以做详细的区分
                else -> throwable.message = "网络错误"
            }
            return throwable
        } else if (e is ServerException) {
            throwable = ResponseThrowable(e.code)
            throwable.message = e.message
            return throwable
        } else if (e is JsonParseException || e is JSONException || e is ParseException) {
            throwable = ResponseThrowable(Error.PARSE_ERROR.type)
            throwable.message = Error.PARSE_ERROR.description
            return throwable
        } else if (e is ConnectException) {
            throwable = ResponseThrowable(Error.NETWORK_ERROR.type)
            throwable.message = Error.NETWORK_ERROR.description
            return throwable
        } else if (e is SSLHandshakeException) {
            throwable = ResponseThrowable(Error.SSL_ERROR.type)
            throwable.message = Error.SSL_ERROR.description
            return throwable
        } else {
            throwable = ResponseThrowable(Error.UNKNOE.type)
            throwable.message = Error.UNKNOE.description
            return throwable
        }
    }


    class ResponseThrowable(code: Int) : Exception() {
        var code = 0
        override var message: String? = null

        init {
            this.code = code
        }

    }

    class ServerException(code:Int,msg:String?) : RuntimeException() {
        var code = 0
        override var message: String? = null
        init {
            this.code = code
            this.message = msg
        }
    }

    enum class Error private constructor(val description: String, val type: Int) {

        UNKNOE("UNKNOW", 1000),
        PARSE_ERROR("PARSE_ERROR", 1001),
        NETWORK_ERROR("NETWORK_ERROR", 1002),
        HTTP_ERROR("HTTP_ERROR", 1003),
        SSL_ERROR("SSL_ERROR", 1004)


    }
}