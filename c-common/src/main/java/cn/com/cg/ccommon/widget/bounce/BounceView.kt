package cn.com.cg.ccommon.widget.bounce

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.OvershootInterpolator
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import cn.com.cg.ccommon.R
import cn.com.cg.ccommon.utils.DeviceUtils
import cn.com.cg.ccommon.widget.bounce.intf.BounceListener
import cn.com.cg.ccommon.widget.bounce.util.BounceInterpolatorType
import cn.com.cg.ccommon.widget.bounce.util.BounceType
import kotlin.math.abs


class BounceView : NestedScrollView {

    private var mTouchSlop = 0

    private var onInterceptTouchDownY = 0
    private var dispatchTouchDowY = 0
    private var bouncingOffset = 2850f
    private var offsetScale = 0f
    private var animator: ValueAnimator? = null
    private var isTop = true
    private val TAG = "BouncingJellyScroolView"

    /**
     * 回调
     */
    private var onBounceListener: BounceListener? = null
    private var mTimeInterpolator //差值器
            : TimeInterpolator? = null
    private var mBounceDuration = 300 //回弹的时间

    private var mBounceType = 0
    private var childView: View? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr(attrs)
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    private fun initAttr(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.BounceView)
            //差值器
            mTimeInterpolator = BounceInterpolatorType.getTimeInterpolator(
                typedArray.getInteger(
                    R.styleable.BounceView_BouncingInterpolator
                    , BounceInterpolatorType.OVERSHOOT_INTERPOLATOR
                )
            )
            //回弹速度
            mBounceDuration = typedArray.getInteger(
                R.styleable.BounceView_BounceDuration,
                mBounceDuration
            )
            //果冻类型
            mBounceType =
                typedArray.getInt(R.styleable.BounceView_BounceType, BounceType.BOTH)
            typedArray.recycle()
            //获取是差值  整个屏幕的三倍大小
            bouncingOffset = (DeviceUtils.getScreenHeight(context) * 3).toFloat()
            //2018年3月6日11:31:59 修复bug，设置子view铺满
            isFillViewport = true
        }
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        Log.e("CG","start onLayout" + System.currentTimeMillis())
        super.onLayout(changed, l, t, r, b)
        childView = getChildAt(0)
        Log.e("CG","end onLayout" + System.currentTimeMillis())
    }

    /**
     * 从顶部开始滑动
     */
    private fun startBouncingTop() {
        Log.e("CG","start startBouncingTo" + System.currentTimeMillis())
        //设置X坐标点
        childView!!.pivotX = width / 2.toFloat()
        //设置Y坐标点
        childView!!.pivotY = 0f
        //进行缩放
        childView!!.scaleY = 1.0f + offsetScale
        onBounceListener?.onBounceTop(1.0f + offsetScale)
        Log.e("CG","end startBouncingTo" + System.currentTimeMillis())
    }

    /**
     * 从底部开始滑动
     */
    private fun startBouncingBottom() {
        Log.e("CG","start startBouncingBottom" + System.currentTimeMillis())
        //设置X坐标点
        childView!!.pivotX = width / 2.toFloat()
        //设置Y坐标点
        childView!!.pivotY = childView!!.height.toFloat()
        //开始缩放
        childView!!.scaleY = 1.0f + offsetScale
        onBounceListener?.onBounceBottom(1.0f + offsetScale)
        Log.e("CG","end startBouncingBottom" + System.currentTimeMillis())
    }


    @SuppressLint("RestrictedApi")
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN ->                 //按下坐标 用于计算缩放值
            {
                Log.e("CG","Action down" + System.currentTimeMillis())
                dispatchTouchDowY = event.rawY.toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                if (mBounceType != BounceType.NONE) {
                    //移动的y轴
                    val moveY = event.rawY.toInt()
                    //dy值 判断方向
                    val dy = moveY - dispatchTouchDowY

                    //y轴滚动
                    val scrollY = scrollY
                    //获得当前高度
                    val getHeight = height
                    //滚动范围高度
                    val computeVerticalScrollRange = computeVerticalScrollRange()

                    //顶部
                    if (dy > 0 && scrollY == 0) {
                        //判断果冻的类型
                        if (mBounceType == BounceType.TOP || mBounceType == BounceType.BOTH) {
                            //获取现在坐标与按下坐标的差值
                            val abs = moveY - dispatchTouchDowY
                            //计算缩放值
                            offsetScale = abs(abs) / bouncingOffset
                            if (offsetScale > 0.3f) {
                                offsetScale = 0.3f
                            }
                            isTop = true
                            startBouncingTop()
                            Log.e("CG","Action move top1" + System.currentTimeMillis())
                            return true
                        }
                    } else if (scrollY == 0 && dy > 0 && offsetScale > 0) { //为顶部 并且dy为下拉 并且缩放值大于0
                        //这一段主要是为了防止卡顿 闪屏
                        if (mBounceType == BounceType.TOP || mBounceType == BounceType.BOTH) {
                            //获取现在坐标与按下坐标的差值
                            val abs = moveY - dispatchTouchDowY
                            //计算缩放值
                            offsetScale = abs(abs) / bouncingOffset
                            if (offsetScale > 0.3f) {
                                offsetScale = 0.3f
                            }
                            if (abs <= 0) {
                                offsetScale = 0f
                                dispatchTouchDowY = moveY
                            }
                            isTop = true
                            startBouncingTop()
                            Log.e("CG","Action move top2" + System.currentTimeMillis())
                            return true
                        }
                    }

                    //底部
                    if (dy < 0 && scrollY + getHeight >= computeVerticalScrollRange) { //滚动到底部
                        if (mBounceType == BounceType.BOTTOM || mBounceType == BounceType.BOTH) {
                            val abs = moveY - dispatchTouchDowY
                            offsetScale = kotlin.math.abs(abs) / bouncingOffset
                            if (offsetScale > 0.3f) {
                                offsetScale = 0.3f
                            }
                            isTop = false
                            startBouncingBottom()
                            Log.e("CG","Action move bottom1" + System.currentTimeMillis())
                            return true
                        }
                    } else if (dy > 0 && scrollY + getHeight >= computeVerticalScrollRange && offsetScale > 0) {
                        // 防止卡顿闪屏
                        if (mBounceType == BounceType.BOTTOM || mBounceType == BounceType.BOTH) {
                            val abs = moveY - dispatchTouchDowY
                            offsetScale = abs(abs) / bouncingOffset
                            if (offsetScale > 0.3f) {
                                offsetScale = 0.3f
                            }
                            if (abs >= 0) {
                                offsetScale = 0f
                                dispatchTouchDowY = moveY
                            }
                            isTop = false
                            startBouncingBottom()
                            Log.e("CG","Action move bottom2" + System.currentTimeMillis())
                            return true
                        }
                    }
                }
                Log.e("CG","Action move3" + System.currentTimeMillis())
            }
            MotionEvent.ACTION_UP ->                 //回滚到初始点
                if (mBounceType != BounceType.NONE) {
                    if (offsetScale > 0) {
                        backBouncing(offsetScale, 0f)
                        Log.e("CG","Action up" + System.currentTimeMillis())
                        return true
                    }
                }
        }
        Log.e("CG","Action dispatchTouchEvent" + System.currentTimeMillis())
        return super.dispatchTouchEvent(event)
    }


    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        Log.e("CG","Action start onInterceptTouchEvent" + System.currentTimeMillis())
        val action = e.action
        Log.e(TAG, "onInterceptTouchEvent: ")
        when (action) {
            MotionEvent.ACTION_DOWN -> onInterceptTouchDownY = e.x.toInt()
            MotionEvent.ACTION_MOVE -> {
                val moveY = e.x.toInt()
                //判断是否达到最小滚动值
                val abs = abs(onInterceptTouchDownY - moveY)
                Log.e(TAG, "onInterceptTouchEvent: 拦截事件:$abs")
                if (abs > mTouchSlop) {
                    Log.e("CG","Action end1 onInterceptTouchEvent" + System.currentTimeMillis())
                    return true
                }
            }
        }
        // 一次不接收，永久不接收
        Log.e("CG","Action end onInterceptTouchEvent" + System.currentTimeMillis())
        return super.onInterceptTouchEvent(e)
    }

    /**
     * 进行回弹
     *
     * @param from
     * @param to
     */
    private fun backBouncing(from: Float, to: Float) {
        Log.e("CG","anim start backBouncing" + System.currentTimeMillis())
        //初始化
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
            animator = null
            offsetScale = 0f
            startBouncingTop()
        }
        if (mTimeInterpolator == null) {
            mTimeInterpolator = OvershootInterpolator()
        }
        //散发值
        animator = ValueAnimator.ofFloat(from, to).setDuration(mBounceDuration.toLong())
        animator?.interpolator = mTimeInterpolator //差值器
        animator?.addUpdateListener(AnimatorUpdateListener { animation ->
            //获取动画阶段的值
            offsetScale = animation.animatedValue as Float
            if (isTop) { //回弹到顶部
                startBouncingTop()
            } else { //回弹到底部
                startBouncingBottom()
            }
        })
        animator?.start()
        Log.e("CG","anim end backBouncing" + System.currentTimeMillis())
    }

    fun setOnBounceListener(onBounceListener: BounceListener?) {
        this.onBounceListener = onBounceListener
    }

    fun setTimeInterpolator(mTimeInterpolator: TimeInterpolator?) {
        this.mTimeInterpolator = mTimeInterpolator
    }

    fun setTimeInterpolatorType() {}

    fun setBounceDuration(mBounceDuration: Int) {
        this.mBounceDuration = mBounceDuration
    }

}