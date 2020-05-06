package cn.com.cg.ccommon.widget.itemdecoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ItemDecorationPowerful : RecyclerView.ItemDecoration {
    companion object{
        const val TAG = "ItemDecorationPowerful"
        //横向布局分割线
        const val HORIZONTAL_DIV = 0
        //纵向布局分割线
        const val VERTICAL_DIV = 1
        //表格布局分割线
        const val GRID_DIV = 2
    }
    var mOrientation:Int = 0
    var mDividerWidth:Int = 0
    lateinit var mPaint:Paint

    /**
     * 默认纵向布分割线
     */
    constructor():this(VERTICAL_DIV)

    /**
     * @param orientation 方向类型
     */
    constructor(orientation:Int):this(orientation, Color.parseColor("#808080"), 2)

    /**
     * @param orientation 方向类型
     * @param color       分割线颜色
     * @param divWidth    分割线宽度
     */
    constructor(orientation:Int, color:Int, divWidth:Int) {
        this.setOrientation(orientation)
        mDividerWidth = divWidth
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = color
        mPaint.style = Paint.Style.FILL
    }

    /**
     * 初始化分割线类型
     *
     * @param orientation 分割线类型
     */
    private fun setOrientation(orientation:Int) {
        if (mOrientation != HORIZONTAL_DIV && mOrientation != VERTICAL_DIV && mOrientation != GRID_DIV) {
            throw IllegalArgumentException("ItemDecorationPowerful：分割线类型设置异常")
        } else {
            this.mOrientation = orientation
        }
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        when(mOrientation){
            HORIZONTAL_DIV -> {
                //横向布局分割线
                drawHorizontal(c, parent)
            }
            VERTICAL_DIV -> {
                //纵向布局分割线
                drawVertical(c, parent)
            }
            GRID_DIV -> {
                //表格格局分割线
                drawGrid(c, parent)
            }
            else -> {
                //纵向布局分割线
                drawVertical(c, parent)
            }
        }
    }

    /**
     * 绘制横向列表分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private fun drawHorizontal(c:Canvas,parent:RecyclerView) {
        val mChildCount = parent.childCount
        for (index in 1..mChildCount){
            val mChild = parent.getChildAt(index - 1)
            drawLeft(c, mChild, parent)
        }
    }

    /**
     * 绘制纵向列表分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private fun drawVertical(c:Canvas,parent:RecyclerView) {
        val mChildCount = parent.childCount
        for (index in 1..mChildCount){
            val mChild = parent.getChildAt(index - 1)
            drawTop(c, mChild, parent)
        }
    }

    /**
     * 绘制表格类型分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private fun drawGrid(c:Canvas,parent:RecyclerView) {
        val mChildCount = parent.childCount
        for (index in 1..mChildCount){
            val mChild = parent.getChildAt(index)
            val mLayoutManager = parent.layoutManager
            if (mLayoutManager is GridLayoutManager) {
                val mSpanCount = mLayoutManager.spanCount
                if (index == 0) {
                    drawTop(c, mChild, parent)
                    drawLeft(c, mChild, parent)
                }
                if ((index + 1) <= mSpanCount) {
                    drawTop(c, mChild, parent)
                }
                if (((index + mSpanCount) % mSpanCount) == 0) {
                    drawLeft(c, mChild, parent)
                }
                drawRight(c, mChild, parent)
                drawBottom(c, mChild, parent)
            }
        }
    }


    /**
     * 绘制右边分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private fun drawLeft(c: Canvas, mChild: View, recyclerView: RecyclerView) {
        val mChildLayoutParams =mChild.layoutParams as RecyclerView.LayoutParams
        val left = mChild.left - mDividerWidth - mChildLayoutParams.leftMargin
        val top = mChild.top - mChildLayoutParams.topMargin
        val right = mChild.left - mChildLayoutParams.leftMargin
        val bottom:Int
        bottom = if (isGridLayoutManager(recyclerView)) {
            mChild.bottom + mChildLayoutParams.bottomMargin + mDividerWidth
        } else {
            mChild.bottom + mChildLayoutParams.bottomMargin
        }
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    /**
     * 绘制顶部分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private fun drawTop(c: Canvas, mChild: View, recyclerView: RecyclerView) {
        val mChildLayoutParams =mChild.layoutParams as RecyclerView.LayoutParams
        val left:Int
        val top = mChild.top - mChildLayoutParams.topMargin - mDividerWidth
        val right = mChild.right + mChildLayoutParams.rightMargin
        val bottom = mChild.top - mChildLayoutParams.topMargin
        left = if (isGridLayoutManager(recyclerView)) {
            mChild.left - mChildLayoutParams.leftMargin - mDividerWidth
        } else {
            mChild.left - mChildLayoutParams.leftMargin
        }
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }


    /**
     * 绘制右边分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private fun drawRight(c: Canvas, mChild: View, recyclerView: RecyclerView) {
        val mChildLayoutParams =mChild.layoutParams as RecyclerView.LayoutParams
        val left = mChild.right + mChildLayoutParams.rightMargin
        val top:Int
        val right = left + mDividerWidth
        val bottom = mChild.bottom + mChildLayoutParams.bottomMargin
        top = if (isGridLayoutManager(recyclerView)) {
            mChild.top - mChildLayoutParams.topMargin - mDividerWidth
        } else {
            mChild.top - mChildLayoutParams.topMargin
        }
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    /**
     * 绘制底部分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private fun drawBottom(c: Canvas, mChild: View, recyclerView: RecyclerView) {
        val mChildLayoutParams =mChild.layoutParams as RecyclerView.LayoutParams
        val left = mChild.left - mChildLayoutParams.leftMargin
        val top = mChild.bottom + mChildLayoutParams.bottomMargin
        val bottom = top + mDividerWidth
        val right:Int
        right = if (isGridLayoutManager(recyclerView)) {
            mChild.right + mChildLayoutParams.rightMargin + mDividerWidth
        } else {
            mChild.right + mChildLayoutParams.rightMargin
        }
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }


    /**
     * 判断RecyclerView所加载LayoutManager是否为GridLayoutManager
     *
     * @param recyclerView RecyclerView
     * @return 是GridLayoutManager返回true，否则返回false
     */
    private fun isGridLayoutManager(recyclerView: RecyclerView): Boolean {
        val mLayoutManager = recyclerView .layoutManager
        return (mLayoutManager is GridLayoutManager)
    }

}