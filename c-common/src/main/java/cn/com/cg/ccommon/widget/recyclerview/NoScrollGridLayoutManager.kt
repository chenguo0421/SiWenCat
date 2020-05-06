package cn.com.cg.ccommon.widget.recyclerview

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

/**
 *  author : chenguo
 *  date : 2020/5/6
 *  description : {  }
 */
class NoScrollGridLayoutManager(context: Context, spanCount: Int) : GridLayoutManager(context,spanCount) {
    private var isScrollEnabled = true
    fun setScrollEnabled(flag: Boolean) {
        isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }
}