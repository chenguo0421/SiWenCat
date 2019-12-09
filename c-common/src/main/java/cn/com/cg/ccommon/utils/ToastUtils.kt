package cn.com.cg.ccommon.utils

import android.view.Gravity
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import android.text.Spanned
import androidx.core.content.ContextCompat
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import cn.com.cg.ccommon.R


/**
 *  author : chenguo
 *  date : 2019/12/6 20:51
 *  description : { Toast工具类 }
 */
public class ToastUtils{

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        // Toast对象
        private var toast: Toast? = null
        // 文字显示的颜色 <color name="white">#FFFFFFFF</color>
        private var messageColor = R.color.common_gray

        /**
         * 在Application中初始化ToastUtils.init(this)
         *
         * @param context
         */
        fun init(context: Context) {
            ToastUtils.context = context.applicationContext
        }

        /**
         * 发送Toast,默认LENGTH_SHORT
         *
         * @param resId
         */
        fun show(resId: Int) {
            showToast(context, context!!.getString(resId), Toast.LENGTH_SHORT)
        }

        /**
         * 发送Toast,默认LENGTH_SHORT
         *
         * @param msg
         */
        fun show(msg: String) {
            showToast(context, msg, Toast.LENGTH_SHORT)
        }

        private fun showToast(context: Context?, massage: String, duration: Int) {
            // 设置显示文字的颜色
            val spannableString = SpannableString(massage)
            val colorSpan = ForegroundColorSpan(ContextCompat.getColor(context!!, messageColor))
            spannableString.setSpan(colorSpan, 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            if (toast == null) {
                toast = Toast.makeText(context, spannableString, duration)
            } else {
                toast!!.setText(spannableString)
                toast!!.duration = duration
            }
            // 设置显示的背景
            val view = toast!!.view
            view.setBackgroundResource(R.drawable.toast_frame_style)
            // 设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移200个单位，
            toast!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, 0, 200)
            toast!!.show()
        }

        /**
         * 在UI界面隐藏或者销毁前取消Toast显示
         */
        fun cancel() {
            if (toast != null) {
                toast!!.cancel()
                toast = null
            }
        }
    }

    private fun ToastUtils() {
        throw UnsupportedOperationException("u can't instantiate me...")
    }








}