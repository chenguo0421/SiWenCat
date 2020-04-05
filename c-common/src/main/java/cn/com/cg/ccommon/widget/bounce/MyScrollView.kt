package cn.com.cg.ccommon.widget.bounce

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ScrollView


class MyScrollView:ScrollView {
    // y方向上当前触摸点的前一次记录位置
    private var previousY = 0

    // y方向上的触摸点的起始记录位置
    private var startY = 0

    // y方向上的触摸点当前记录位置
    private var currentY = 0

    // y方向上两次移动间移动的相对距离
    private var deltaY = 0

    // 第一个子视图
    private lateinit var childView: View

    // 用于记录childView的初始位置
    private val topRect: Rect = Rect()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @SuppressLint("MissingSuperCall")
    override fun onFinishInflate() {
        if (childCount > 0) {
            childView = getChildAt(0)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startY = event.y.toInt()
                previousY = startY
            }
            MotionEvent.ACTION_MOVE -> {
                currentY = event.y.toInt()
                deltaY = previousY - currentY
                previousY = currentY
                if (0 == scrollY
                    || childView.measuredHeight - height <= scrollY
                ) {
                    // 记录childView的初始位置
                    if (topRect.isEmpty) {
                        topRect.set(
                            childView.left, childView.top,
                            childView.right, childView.bottom
                        )
                    }
                    // 更新childView的位置
                    childView.layout(
                        childView.left, childView.top
                                - deltaY / 3, childView.right,
                        childView.bottom - deltaY / 3
                    )
                }
            }
            MotionEvent.ACTION_UP -> {
                if (!topRect.isEmpty) {
                    upDownMoveAnimation()
                    // 子控件回到初始位置
                    childView.layout(
                        topRect.left, topRect.top, topRect.right,
                        topRect.bottom
                    )
                }
                startY = 0
                currentY = 0
                topRect.setEmpty()
            }
            else -> {
            }
        }
        return super.dispatchTouchEvent(event)
    }

    // 初始化上下回弹的动画效果
    private fun upDownMoveAnimation() {
        val animation = TranslateAnimation(
            0.0f, 0.0f,
            childView.top.toFloat(), topRect.top.toFloat()
        )
        animation.duration = 100
        animation.interpolator = AccelerateInterpolator()
        childView.animation = animation
    }
}