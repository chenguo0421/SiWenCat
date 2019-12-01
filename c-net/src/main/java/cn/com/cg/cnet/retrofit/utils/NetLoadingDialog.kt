package cn.com.cg.cnet.retrofit.utils

import android.app.Dialog
import android.content.Context
import cn.com.cg.cnet.R

/**
 *  author : chenguo
 *  date : 2019/12/1 19:17
 *  description : { 请添加该类的描述 }
 */
object  NetLoadingDialog{
    private var dialog: Dialog? = null

    fun show(context: Context) {
        cancel()
        dialog = Dialog(context, R.style.LoadingDialog)
        dialog?.setContentView(R.layout.dialog_layout)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }
    fun cancel() {
        dialog?.dismiss()
    }
}