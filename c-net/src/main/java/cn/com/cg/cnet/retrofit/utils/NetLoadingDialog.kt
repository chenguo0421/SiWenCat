package cn.com.cg.cnet.retrofit.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import cn.com.cg.cnet.R
import cn.com.cg.cnet.retrofit.widget.RotateLoading
/**
 *  author : chenguo
 *  date : 2019/12/1 19:17
 *  description : { 请添加该类的描述 }
 */
object  NetLoadingDialog{
    private var rotateloading: RotateLoading? = null
    private var dialog: Dialog? = null



    fun show(context: Context) {
        cancel()
        dialog = Dialog(context, R.style.LoadingDialog)
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
        dialog?.setContentView(view)
        rotateloading = view.findViewById(R.id.rotateloading)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
        if (!rotateloading!!.isStart()){
            rotateloading!!.start()
        }
    }

    fun cancel() {
        if (rotateloading != null && rotateloading!!.isStart()) {
            rotateloading!!.stop()
        }
        dialog?.dismiss()
    }
}