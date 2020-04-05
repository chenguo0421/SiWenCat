package cn.com.cg.ccommon.widget.bounce

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.TranslateAnimation
import android.widget.ScrollView
import kotlin.math.abs

class JellyScrollView:ScrollView {
    private lateinit var childView: View//子view
    var mY:Float = 0.0f// 点击时y坐标
    var normal: Rect = Rect()// 矩形(这里只是个形式，只是用于判断是否需要动画.)
    var isCount:Boolean = false// 是否开始计算
    var isMoving: Boolean = false// 是否开始移动.
    var mTop:Int = 0// 拖动时时高度。
    var mTouchSlop:Int//系统最少滑动距离
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop;
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) { childView = getChildAt(0); }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var action = ev?.action
        when(action){
            MotionEvent.ACTION_DOWN->{
                y = ev?.y!!
                top = 0
            }

            MotionEvent.ACTION_UP->{
                isMoving = false
                if (isNeedAnimation()) { animation() }
            }

            MotionEvent.ACTION_MOVE->{
                val preY:Float = y// 按下时的y坐标
                var nowY:Float = ev?.y!!// 每时刻y坐标
                var deltaY:Int = (nowY - preY).toInt()// 滑动距离
                if (!isCount) { deltaY = 0 // 在这里要归0.
                    if (abs(deltaY) < mTouchSlop && top <= 0) return true// 当滚动到最上或者最下时就不会再滚动，这时移动布局 isNeedMove();

                    if (isMoving) {
                        // 初始化头部矩形
                        if (normal.isEmpty) {
                            // 保存正常的布局位置
                            normal.set(childView.left, childView.top, childView.right, childView.bottom);
                        }

                        // 移动布局
                        childView.layout(childView.left, childView.top + deltaY / 3, childView.right, childView.bottom + deltaY / 3);
                        top += (deltaY / 6);
                    }
                    isCount = true;
                    y = nowY;
            }
            }
        }
        return super.onInterceptTouchEvent(ev);

    }



        /*** * 回缩动画 */
        fun animation() {
            // 开启移动动画
            val ta = TranslateAnimation(0f, 0f, childView.top.toFloat(), normal.top.toFloat())
            ta.duration = 200
            childView.startAnimation(ta)
            // 设置回到正常的布局位置
            childView.layout(normal.left, normal.top, normal.right, normal.bottom); normal.setEmpty()
            // 手指松开要归0.
            isCount = false
            y = 0f
        }


        // 是否需要开启动画
        fun isNeedAnimation():Boolean {
            return !normal.isEmpty
        }

        /***
         * 是否需要移动布局 *
         * inner.getMeasuredHeight():获取的是控件的总高度 *
         * getHeight()：获取的是屏幕的高度*/
         fun isNeedMove() {
            var offset = childView.measuredHeight - height
            var scrollY = scrollY
            // scrollY == 0是顶部 // scrollY == offset是底部
            if (scrollY == 0 || scrollY == offset) { isMoving = true }
        }
}