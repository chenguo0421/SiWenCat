package cn.com.cg.cnet.retrofit.observelistener

import android.content.Context
import cn.com.cg.cnet.retrofit.exception.ExceptionHandler
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/11/25 15:09
 */
public class ProgressObserver(
    context: Context,
    listener: ObserveResponseListener
) : Observer<Any> {
    private val TAG = "ProgressObserver--->"

    private var mObserveResponseListener: ObserveResponseListener? = null
    private var mContext: Context? = null
    private var disposable: Disposable? = null


    override fun onSubscribe(d: Disposable) {
        this.disposable = d
    }

    override fun onNext(o: Any) {
        mObserveResponseListener?.onNext(o)
    }

    override fun onError(e: Throwable) {
        if (e is ExceptionHandler.ResponseThrowable) {
            mObserveResponseListener?.onError(e as ExceptionHandler.ResponseThrowable)
        } else {
            mObserveResponseListener?.onError(ExceptionHandler.ResponseThrowable(ExceptionHandler.Error.UNKNOE.type))
        }

    }

    override fun onComplete() {

    }
}