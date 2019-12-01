package cn.com.cg.ccommon.widget.circleimageview

import androidx.appcompat.widget.AppCompatImageView
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import cn.com.cg.ccommon.R

/**
 *  author : chenguo
 *  date : 2019/11/30 22:08
 *  description : { 圆形图片 }
 */
class CircleImageView : AppCompatImageView {

    companion object{
        private val SCALE_TYPE:ScaleType = ScaleType.CENTER_CROP
        private val BITMAP_CONFIG:Bitmap.Config = Bitmap.Config.ARGB_8888
        private const val COLORDRAWABLE_DIMENSION:Int = 2
        private const val DEFAULT_BORDER_WIDTH:Int = 0
        private const val DEFAULT_BORDER_COLOR:Int = Color.BLACK
        private const val DEFAULT_CIRCLE_BACKGROUND_COLOR:Int = Color.TRANSPARENT
        private const val DEFAULT_BORDER_OVERLAY:Boolean = false
    }




    private val  mDrawableRect:RectF = RectF()
    private val  mBorderRect:RectF = RectF()

    private val mShaderMatrix:Matrix = Matrix()
    private val mBitmapPaint:Paint =Paint()
    private val mBorderPaint:Paint =Paint()
    private val mCircleBackgroundPaint:Paint =Paint()

    private var mBorderColor:Int = DEFAULT_BORDER_COLOR
    private var mBorderWidth:Int = DEFAULT_BORDER_WIDTH
    private var mCircleBackgroundColor:Int = DEFAULT_CIRCLE_BACKGROUND_COLOR

    private var mBitmap:Bitmap? = null
    private var mBitmapShader:BitmapShader? = null
    private var mBitmapWidth:Int = 0
    private var mBitmapHeight:Int = 0

    private var mDrawableRadius:Float = 0f
    private var mBorderRadius:Float = 0f

    private var mColorFilter:ColorFilter? = null

    private var mReady:Boolean = false
    private var mSetupPending:Boolean = false
    private var mBorderOverlay:Boolean = false
    private var mDisableCircularTransformation:Boolean = false


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var a:TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0)
        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, DEFAULT_BORDER_WIDTH)
        mBorderColor = a?.getColor(R.styleable.CircleImageView_civ_border_color, DEFAULT_BORDER_COLOR)
        mBorderOverlay = a?.getBoolean(R.styleable.CircleImageView_civ_border_overlay, DEFAULT_BORDER_OVERLAY)
        mCircleBackgroundColor = a?.getColor(R.styleable.CircleImageView_civ_circle_background_color, DEFAULT_CIRCLE_BACKGROUND_COLOR)
        a.recycle()
        init()
    }




    private fun init() {
        super.setScaleType(SCALE_TYPE)
        mReady = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = OutlineProvider()
        }

        if (mSetupPending) {
            setup()
            mSetupPending = false
        }
    }

    override fun getScaleType():ScaleType {
        return SCALE_TYPE
    }

    override fun setScaleType (scaleType:ScaleType) {
        if (scaleType != SCALE_TYPE) {
            throw IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType))
        }
    }

    override fun setAdjustViewBounds (adjustViewBounds:Boolean) {
        if (adjustViewBounds) {
            throw IllegalArgumentException("adjustViewBounds not supported.")
        }
    }

    override fun onDraw(canvas:Canvas) {
        if (mDisableCircularTransformation) {
            super.onDraw(canvas)
            return
        }

        if (mBitmap == null) {
            return
        }

        if (mCircleBackgroundColor != Color.TRANSPARENT) {
            canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mCircleBackgroundPaint)
        }
        canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mBitmapPaint)
        if (mBorderWidth > 0) {
            canvas.drawCircle(mBorderRect.centerX(), mBorderRect.centerY(), mBorderRadius, mBorderPaint)
        }
    }

    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    override fun setPadding(left:Int, top:Int, right:Int, bottom:Int) {
        super.setPadding(left, top, right, bottom)
        setup()
    }

    override fun setPaddingRelative(start:Int, top:Int, end:Int, bottom:Int) {
        super.setPaddingRelative(start, top, end, bottom)
        setup()
    }

    public fun getBorderColor():Int {
        return mBorderColor
    }

    public fun setBorderColor(@ColorInt borderColor:Int) {
        if (borderColor == mBorderColor) {
            return
        }

        mBorderColor = borderColor
        mBorderPaint.color = mBorderColor
        invalidate()
    }

    public fun getCircleBackgroundColor():Int {
        return mCircleBackgroundColor
    }

    public fun setCircleBackgroundColor(@ColorInt circleBackgroundColor:Int) {
        if (circleBackgroundColor == mCircleBackgroundColor) {
            return
        }

        mCircleBackgroundColor = circleBackgroundColor
        mCircleBackgroundPaint.setColor(circleBackgroundColor)
        invalidate()
    }

    public fun setCircleBackgroundColorResource(@ColorRes circleBackgroundRes:Int) {
        setCircleBackgroundColor(getContext().getResources().getColor(circleBackgroundRes))
    }

    public fun getBorderWidth():Int {
        return mBorderWidth
    }

    public fun setBorderWidth(borderWidth:Int) {
        if (borderWidth == mBorderWidth) {
            return
        }

        mBorderWidth = borderWidth
        setup()
    }

    public fun isBorderOverlay():Boolean{
        return mBorderOverlay
    }

    public fun setBorderOverlay(borderOverlay:Boolean) {
        if (borderOverlay == mBorderOverlay) {
            return
        }

        mBorderOverlay = borderOverlay
        setup()
    }

    public fun isDisableCircularTransformation():Boolean {
        return mDisableCircularTransformation
    }

    public fun setDisableCircularTransformation(disableCircularTransformation:Boolean) {
        if (mDisableCircularTransformation == disableCircularTransformation) {
            return
        }

        mDisableCircularTransformation = disableCircularTransformation
        initializeBitmap()
    }

    override fun setImageBitmap(bm:Bitmap) {
        super.setImageBitmap(bm)
        initializeBitmap()
    }

    override fun setImageDrawable(drawable:Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
    }

    override fun setImageResource(@DrawableRes resId:Int) {
        super.setImageResource(resId)
        initializeBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        initializeBitmap()
    }

    override fun setColorFilter(cf:ColorFilter) {
        if (cf == mColorFilter) {
            return
        }

        mColorFilter = cf
        applyColorFilter()
        invalidate()
    }


    override fun getColorFilter(): ColorFilter? {
        return mColorFilter
    }

    private fun applyColorFilter() {
        mBitmapPaint.colorFilter = mColorFilter
    }

    private fun getBitmapFromDrawable(drawable:Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        try {
            var bitmap:Bitmap? = if (drawable is ColorDrawable) {
                Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG)
            } else {
                Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, BITMAP_CONFIG)
            }

            var canvas:Canvas = Canvas(bitmap!!)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } catch (e:Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun initializeBitmap() {
        if (mDisableCircularTransformation) {
            mBitmap = null
        } else {
            mBitmap = getBitmapFromDrawable(drawable)
        }
        setup()
    }

    private fun setup() {
        if (!mReady) {
            mSetupPending = true
            return
        }

        if (width == 0 && height == 0) {
            return
        }

        if (mBitmap == null) {
            invalidate()
            return
        }

        mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        mBitmapPaint.isAntiAlias = true
        mBitmapPaint.shader = mBitmapShader

        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.isAntiAlias = true
        mBorderPaint.color = mBorderColor
        mBorderPaint.strokeWidth = mBorderWidth.toFloat()

        mCircleBackgroundPaint.style = Paint.Style.FILL
        mCircleBackgroundPaint.isAntiAlias = true
        mCircleBackgroundPaint.color = mCircleBackgroundColor

        mBitmapHeight = mBitmap!!.getHeight()
        mBitmapWidth = mBitmap!!.getWidth()

        mBorderRect.set(calculateBounds())
        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2.0f, (mBorderRect.width() - mBorderWidth) / 2.0f)

        mDrawableRect.set(mBorderRect)
        if (!mBorderOverlay && mBorderWidth > 0) {
            mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f)
        }
        mDrawableRadius = Math.min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f)

        applyColorFilter()
        updateShaderMatrix()
        invalidate()
    }


    private fun calculateBounds():RectF{
        var availableWidth:Int = width - paddingLeft - paddingRight
        var availableHeight:Int = height - paddingTop - paddingBottom
        var sideLength:Int = Math.min(availableWidth,availableHeight)
        var left = paddingLeft + (availableWidth - sideLength) / 2f
        var top = paddingTop + (availableHeight - sideLength) / 2f
        return RectF(left, top, left + sideLength, top + sideLength)

    }


    private fun updateShaderMatrix(){
        var scale:Float? = 0f
        var dx:Float? = 0f
        var dy:Float? = 0f
        mShaderMatrix.set(null)
        if(mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight){
            scale = mDrawableRect.height() / mBitmapHeight
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f
        }else{
            scale = mDrawableRect.width() / mBitmapWidth
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f
        }
        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate((dx!! + 0.5f) + mDrawableRect.left, (dy!! + 0.5f) + mDrawableRect.top)

        mBitmapShader?.setLocalMatrix(mShaderMatrix)

    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (mDisableCircularTransformation){
            return super.onTouchEvent(event)
        }
        return inTouchableArea(event!!.x, event!!.y) && super.onTouchEvent(event)
    }

    private fun inTouchableArea(x: Float, y: Float):Boolean {
        if (mBorderRect.isEmpty) {
            return true
        }
        return Math.pow((x - mBorderRect.centerX()).toDouble(), 2.0) + Math.pow((y - mBorderRect.centerY()).toDouble(),
            2.0
        ) <= Math.pow(
            mBorderRadius.toDouble(), 2.0
        )
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    inner class OutlineProvider:ViewOutlineProvider() {


        override fun getOutline(view: View?, outline: Outline?) {
            val bounds = Rect()
            mBorderRect.roundOut(bounds)
            outline?.setRoundRect(bounds, bounds.width() / 2.0f)
        }
    }

}