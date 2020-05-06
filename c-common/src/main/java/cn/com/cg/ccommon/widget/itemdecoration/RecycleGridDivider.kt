package cn.com.cg.ccommon.widget.itemdecoration

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *  author : chenguo
 *  date : 2020/5/6
 *  description : {  }
 */
class RecycleGridDivider : RecyclerView.ItemDecoration{
    private var space = 0
    private var color = 0
    private var mPaint: Paint? = null

    /**
     * 默认的，垂直方向 横纵1px 的分割线 颜色透明
     */
    constructor() : this(1)

    /**
     * 自定义宽度的透明分割线
     *
     * @param space 指定宽度
     */
    constructor(space: Int) :this(space, Color.TRANSPARENT)

    /**
     * 自定义宽度，并指定颜色的分割线
     *
     * @param space 指定宽度
     * @param color 指定颜色
     */
    constructor(space: Int, color: Int) {
        this.space = space
        this.color = color
        initPaint()
    }


    private fun initPaint() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint?.color = color
        mPaint?.style = Paint.Style.FILL
        mPaint?.strokeWidth = space.toFloat()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val manager: GridLayoutManager = parent.layoutManager as GridLayoutManager
        val childSize: Int = parent.childCount
        val span: Int = manager.spanCount
        //为了Item大小均匀，将设定分割线平均分给左右两边Item各一半
        val offset = space / 2
        //得到View的位置
        val childPosition: Int = parent.getChildAdapterPosition(view)
        //第一排，顶部不画
        if (childPosition < span) {
            //最左边的，左边不画
            when {
                childPosition % span == 0 -> {
                    outRect.set(0, 0, offset, 0)
                    //最右边，右边不画
                }
                childPosition % span == span - 1 -> {
                    outRect.set(offset, 0, 0, 0)
                }
                else -> {
                    outRect.set(offset, 0, offset, 0)
                }
            }
        } else {
            //上下的分割线，就从第二排开始，每个区域的顶部直接添加设定大小，不用再均分了
            when {
                childPosition % span == 0 -> {
                    outRect.set(0, space, offset, 0)
                }
                childPosition % span == span - 1 -> {
                    outRect.set(offset, space, 0, 0)
                }
                else -> {
                    outRect.set(offset, space, offset, 0)
                }
            }
        }
    }

}