package cn.com.cg.clog.view

import android.content.Context
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView



/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/9/17 20:37
 */
class CLogRecyclerView(context: Context):RecyclerView(context){



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }
}