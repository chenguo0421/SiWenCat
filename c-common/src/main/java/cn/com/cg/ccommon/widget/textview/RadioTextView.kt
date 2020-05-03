package cn.com.cg.ccommon.widget.textview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import cn.com.cg.ccommon.R
import com.pdog.dimension.dp

class RadioTextView : View{

    private lateinit var rec: RectF

    /**
     * title文本
     */
    private var mTitleText:String? = ""
    /**
     * title文本的颜色
     */
    var mTitleTextColor:Int = R.color.common_white
    /**
     * titel文本的大小
     */
    var mTitleTextSize:Int = 16.dp


    /**
     * background
     * @param context
     * @param attrs
     */
    var background:Int = R.color.common_text_color_333333

    /**
     * 圆角大小
     */
    var mCornerSize:Int = 0

    /**
     * 绘制时控制文本绘制的范围
     */
    lateinit var mtitleBound:Rect
    lateinit var mtitlePaint: Paint

    lateinit var paint: Paint


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RadioTextView, defStyleAttr, 0)
        mTitleText = typedArray.getString(R.styleable.RadioTextView_rtv_text)
        mTitleTextColor = typedArray.getColor(R.styleable.RadioTextView_rtv_text_color, context.resources.getColor(R.color.common_white,context.resources.newTheme()))
        mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.RadioTextView_rtv_text_size, mTitleTextSize)
        background = typedArray.getColor(R.styleable.RadioTextView_rtv_background_color, context.resources.getColor(R.color.common_color_e74b4b,context.resources.newTheme()))
        mCornerSize = typedArray.getDimensionPixelSize(R.styleable.RadioTextView_rtv_radio,0)
        typedArray.recycle();
        init()
    }


    fun init(){
        /**
         * 获得绘制文本的宽和高
         */
        mtitlePaint = Paint()
        mtitlePaint.textSize = mTitleTextSize.toFloat()
        mtitleBound = Rect()
        mTitleText?.let {
            mtitlePaint.getTextBounds(mTitleText, 0, mTitleText!!.length, mtitleBound);
        }

        paint = Paint(Paint.FILTER_BITMAP_FLAG)
        rec = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
    }

    fun setContentText(text:String?){
        this.mTitleText = text ?: ""
        init()
    }

    fun setContentTextColor(parseColor: Int) {
        this.mTitleTextColor = parseColor

    }

    fun setContentBackgroundColor(parseColor: Int) {
        this.background = parseColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var w:Int = 0
        var h:Int = 0
        if (widthMode == MeasureSpec.EXACTLY)
        {
            w = widthSize
        } else
        {
            mtitlePaint.textSize = mTitleTextSize.toFloat()
            mTitleText?.let {
                mtitlePaint.getTextBounds(mTitleText, 0, mTitleText!!.length, mtitleBound)
            }


            val desired:Int = paddingLeft + mtitleBound.width() + paddingRight
            w = if (desired <= widthSize) desired else widthSize

        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            h = heightSize
        } else
        {
            mtitlePaint.textSize = mTitleTextSize.toFloat()
            mTitleText?.let {
                mtitlePaint.getTextBounds(mTitleText, 0, mTitleText!!.length, mtitleBound);
            }
            val desired = paddingTop + mtitleBound.height() + paddingBottom;
            h = if (desired <= heightSize) desired else heightSize
        }
        setMeasuredDimension(w, h)
    }


    override fun onDraw(canvas: Canvas?) {
         paint.isAntiAlias = true
        paint.color = background
        rec.left = 0f
        rec.top = 0f
        rec.right = measuredWidth.toFloat()
        rec.bottom = measuredHeight.toFloat()
        canvas?.drawRoundRect(rec, mCornerSize.toFloat(), mCornerSize.toFloat(), paint)

        mTitleText?.let {
            mtitlePaint.color = mTitleTextColor
            val fontMetrics = mtitlePaint.fontMetricsInt
            val baseline = (measuredHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top
            canvas?.drawText(mTitleText!!, paddingLeft.toFloat(), baseline.toFloat(), mtitlePaint)
        }
    }


}