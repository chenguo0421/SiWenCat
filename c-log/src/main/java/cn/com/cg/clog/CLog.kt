package cn.com.cg.clog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.*
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import cn.com.cg.clog.adapter.CLogAdapter
import cn.com.cg.clog.annotation.IgnoreCLogView
import cn.com.cg.clog.utils.CheckLeak
import cn.com.cg.clog.utils.LogType
import cn.com.cg.clog.view.CLogRecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/9/16 16:05
 */
public class CLog(context: Context) : FrameLayout(context),Thread.UncaughtExceptionHandler,
    Application.ActivityLifecycleCallbacks, View.OnClickListener, View.OnLongClickListener{


    private lateinit var switchLog: AppCompatImageView
    private var mShortClick: Int = 0
    private var timestamp: Long = 0L
    private lateinit var btnFrontPage: AppCompatImageView
    private lateinit var btnNextPage: AppCompatImageView
    private lateinit var mSrcView: View
    private lateinit var mCurrentActivity: Activity
    private val cyclicTime:Long = 2000L

    private lateinit var mLeakCheck: CheckLeak
    private val mFilterList: ArrayList<String> = ArrayList()
    private val mLogList = java.util.ArrayList<String>()



    private lateinit var mLogAdapter: CLogAdapter
    private lateinit var mRVLog: CLogRecyclerView
    private lateinit var mLogContainer: RelativeLayout
    private var count:Int = 0

    private val SHORT_CLICK = 19
    private val LONG_CLICK = 3


    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null


    private val mRunnable = object : Runnable {
        override fun run() {
            handler.removeCallbacks(this)
            handler.postDelayed(this, cyclicTime) //轮询检测
            var s: String? = null
            try {
                s = mLeakCheck.checkLeak()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            if (TextUtils.isEmpty(s)) return
        }
    }


    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val text = msg.obj as String
            addText(msg.what, text)
        }
    }



    override fun onClick(view: View?) {
        when(view){
            btnFrontPage ->{
                showFrontPage()
            }
            btnNextPage ->{
                showNextPage()
            }
            switchLog -> {
                loggerSwitch(true)
            }
        }
    }


    override fun onLongClick(view: View?): Boolean {
        when(view){
            switchLog -> {
                loggerSwitch(false)
                return true
            }
        }
        return false
    }


    /**
     * 显示下一页日志
     * 规则：若当前最后一条可见的item后还有大于5条日志，则将往前滑动五条，否则直接滑到底
     */
    private fun showNextPage() {
        val manager = mRVLog.layoutManager as LinearLayoutManager
        val indexLast = manager.findLastVisibleItemPosition()
        if (mRVLog.adapter != null) {
            scollToTopPosition(indexLast)
        }
    }

    /**
     * 显示上一页日志
     * 规则：若当前第一条可见的item前边还有大于5条日志，则将往前滑动五条，否则直接滑到顶
     */
    private fun showFrontPage() {
        val manager = mRVLog.layoutManager as LinearLayoutManager
        val indexLast = manager.findLastVisibleItemPosition()
        if (mRVLog.adapter != null) {
            scollToBottomPosition(indexLast)
        }
    }


    /**
     * 将指定Item滑动到顶部
     */
    private fun scollToTopPosition(n: Int) {
        //滑动到指定的item
        val manager = mRVLog.layoutManager as LinearLayoutManager
        //拿到当前屏幕可见的第一个position跟最后一个postion
        val firstItem = manager.findFirstVisibleItemPosition()
        val lastItem = manager.findLastVisibleItemPosition()
        //区分情况
        when {
            n <= firstItem -> //当要置顶的项在当前显示的第一个项的前面时
                mRVLog.smoothScrollToPosition(n)
            n <= lastItem -> {
                //当要置顶的项已经在屏幕上显示时
                val top = mRVLog.getChildAt(n - firstItem).top
                mRVLog.smoothScrollBy(0, top)
            }
            else -> //当要置顶的项在当前显示的最后一项的后面时
                mRVLog.smoothScrollToPosition(n)
        }
    }

    /**
     * 将指定Item滑动到底部
     */
    private fun scollToBottomPosition(n: Int) {
        //滑动到指定的item
        val manager = mRVLog.layoutManager as LinearLayoutManager
        //拿到当前屏幕可见的第一个position跟最后一个postion
        val firstItem = manager.findFirstVisibleItemPosition()
        val lastItem = manager.findLastVisibleItemPosition()
        //区分情况
        when {
            n <= firstItem -> //当要置顶的项在当前显示的第一个项的前面时
                mRVLog.smoothScrollToPosition(n)
            n <= lastItem -> {
                //当要置顶的项已经在屏幕上显示时
                val top = mRVLog.getChildAt(n - firstItem).top
                mRVLog.smoothScrollBy(0, -top)
            }
            else -> //当要置顶的项在当前显示的最后一项的后面时
                mRVLog.smoothScrollToPosition(n)
        }
    }

    private fun addText(type: Int, text: String) {
        val level = arrayOf("#000000", "", "#000000", "#2FB1FE", "#00ff00", "#EFC429", "#FF0000")
        val str = String.format("<font color=\"" + level[type] + "\">%s</font>", text)
        mLogList.add(str)
//        while (mLogList.size > 100)  mLogList.removeAt(0)
        refreshList()
    }


    /*刷新日志列表*/
    private fun refreshList() {
//        mFilterList.clear()//清空过滤列表
        for (i in mLogList.indices) {
            val s = mLogList[i]
            var l = 2
            for (j in 2..6) {
                val level1 = getLevel(j)
                if (s.contains("]$level1/")) {
                    l = j
                    break
                }
            }
        }
        mLogAdapter.notifyDataSetChanged()

        if (isAutoScroll){
            mRVLog.smoothScrollToPosition(mLogAdapter.itemCount - 1)
        }
    }



    private fun initAttr(app:Application){
        app.registerActivityLifecycleCallbacks(this)
        //获取系统默认异常处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //线程空闲时设置异常处理，兼容其他框架异常处理能力
        Looper.myQueue().addIdleHandler {
            Thread.setDefaultUncaughtExceptionHandler(instance)//线程异常处理设置为自己
            false
        }

        val typeValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4F, resources.displayMetrics)
        logTag = context.applicationInfo.packageName //可以自定义


        //日志容器
        mLogContainer = RelativeLayout(context)
        mLogContainer.setBackgroundColor(Color.argb(0x22, 0Xff, 0xff, 0xff))
        val widthPixels = context.resources.displayMetrics.widthPixels
        val heightPixels = context.resources.displayMetrics.heightPixels
        val layoutParams = LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        layoutParams.topMargin = 100
        layoutParams.bottomMargin = 100
        mLogContainer.layoutParams = layoutParams
        mLogContainer.visibility = View.INVISIBLE


        //日志列表
        mRVLog = CLogRecyclerView(context)
        var lManager = LinearLayoutManager(context)
        mRVLog.layoutParams = LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        mRVLog.layoutManager = lManager
        mLogContainer.addView(mRVLog)
        mRVLog.visibility = View.INVISIBLE


        //日志列表适配器
        mLogAdapter = CLogAdapter(context, mLogList,typeValue)
        mRVLog.adapter = mLogAdapter


        //上一页按钮
        btnFrontPage = AppCompatImageView(context)
        btnFrontPage.setImageResource(R.drawable.common_angles_up_sm)
        val lpFront = RelativeLayout.LayoutParams(widthPixels/10,widthPixels/10)
        lpFront.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        lpFront.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        lpFront.bottomMargin = (widthPixels/10 * 2.5).toInt()
        lpFront.rightMargin = widthPixels/15

        btnFrontPage.layoutParams = lpFront
        btnFrontPage.setOnClickListener(this)
        btnFrontPage.visibility = View.INVISIBLE

        //下一页按钮
        btnNextPage = AppCompatImageView(context)
        btnNextPage.setImageResource(R.drawable.common_angles_down_sm)
        val lpNext = RelativeLayout.LayoutParams(widthPixels/10,widthPixels/10)
        lpNext.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        lpNext.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        lpNext.bottomMargin = widthPixels/10
        lpNext.rightMargin = widthPixels/15
        btnNextPage.layoutParams = lpNext
        btnNextPage.setOnClickListener(this)
        btnNextPage.visibility = View.INVISIBLE

        mLogContainer.addView(btnFrontPage)
        mLogContainer.addView(btnNextPage)


        //日志开关，
        switchLog = AppCompatImageView(context)
        switchLog.setImageResource(R.drawable.drawable_switchlog)
        val sp = RelativeLayout.LayoutParams(widthPixels/10,widthPixels/10)
        sp.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        sp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        sp.topMargin = widthPixels/10
        sp.rightMargin = widthPixels/15
        switchLog.layoutParams = sp
        switchLog.setOnClickListener(this)
        switchLog.setOnLongClickListener(this)
        switchLog.visibility = View.INVISIBLE

        mLogContainer.addView(switchLog)




        //检测内存泄漏相关
        mLeakCheck = CheckLeak()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(mRunnable, cyclicTime)

    }


    /**
     * 触屏事件拦截，连续快速点击二十次，显示日志开关
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev?.action
        if (action == MotionEvent.ACTION_DOWN) {
            val l = SystemClock.uptimeMillis()
            val dis = l - timestamp
            logSwitch(dis)
            timestamp = SystemClock.uptimeMillis()
        }
        return super.dispatchTouchEvent(ev)
    }



    private fun logSwitch(dis: Long) {
        if (dis <= 500) {
            mShortClick++
            if (mShortClick > SHORT_CLICK) clearClick()
        }

        if (dis > 500) {
            clearClick()
        }

        if (mShortClick == SHORT_CLICK) {
            showLogSwitchBtn()
        }
    }

    private fun showLogSwitchBtn() {
        if((mLogContainer.visibility == View.INVISIBLE).or(mLogContainer.visibility == View.GONE)){
            mLogContainer.visibility = View.VISIBLE
            mRVLog.visibility = View.INVISIBLE
            btnFrontPage.visibility = View.INVISIBLE
            btnNextPage.visibility = View.INVISIBLE
            switchLog.visibility = View.VISIBLE
        }else{
            switchLog.visibility = View.VISIBLE
        }
    }

    private fun loggerSwitch(isClose:Boolean) {
        if (!isClose){//关闭调试模式，清空日志
            mLogContainer.visibility = View.INVISIBLE
            mLogList.clear()
        }else{//显示/隐藏日志
            if ((mRVLog.visibility == View.INVISIBLE).or(mRVLog.visibility == View.GONE)) {
                mRVLog.visibility = View.VISIBLE
                btnFrontPage.visibility = View.VISIBLE
                btnNextPage.visibility = View.VISIBLE
                switchLog.visibility = View.VISIBLE
            } else {
                mRVLog.visibility = View.INVISIBLE
                btnFrontPage.visibility = View.INVISIBLE
                btnNextPage.visibility = View.INVISIBLE
            }
        }
        clearClick()
    }

    private fun clearClick() {
        mShortClick = 0
    }


    private fun print(type: Int, tag: String, msg: String) {
//        if (!debuggable || instance == null || type < mFilterLevel + 2) return
        val str = "[" + getTime() + "]" + getLevel(type) + "/" + tag + ":" + msg
//        if (!TextUtils.isEmpty(mFilterText) && !str.contains(mFilterText)) return
        handler.obtainMessage(type, str).sendToTarget()
        var start = 0
        var end = 0
        while (end < msg.length) {
            end = if (start + 3000 > msg.length) msg.length else start + 3000
            val subMsg = msg.substring(start, end)
            start = end
            when (type) {
                Log.VERBOSE -> Log.v(tag, subMsg)
                Log.DEBUG -> Log.d(tag, subMsg)
                Log.INFO -> Log.i(tag, subMsg)
                Log.WARN -> Log.w(tag, subMsg)
                Log.ERROR -> Log.e(tag, subMsg)
                logScout -> println("$tag:$subMsg")
            }
        }
    }


    private fun getLevel(type: Int): String {
        val level = arrayOf("S", "", "V", "D", "I", "W", "E")
        return level[type]
    }

    private fun getTime(): String {
        return SimpleDateFormat(
            "MM-dd HH:mm:ss.SSS",
            Locale.getDefault()
        ).format(Date())
    }



    override fun onActivityPaused(activity: Activity) {
        if (needIgnore(activity)) return
        val decorView = activity.window.decorView as ViewGroup
        instance?.removeView(mSrcView)
        instance?.removeView(mLogContainer)
        decorView.removeView(instance!!)
        decorView.addView(mSrcView, 0)
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
        if (debuggable) {
            if (needIgnore(activity)) return
            val decorView = activity.window.decorView as ViewGroup
            mSrcView = decorView.getChildAt(0)
            decorView.removeView(mSrcView)
            instance?.addView(mSrcView, 0)
            if (mLogContainer.parent == null) {
                instance?.addView(mLogContainer, 1)
            }
            if (instance?.parent == null) {
                decorView.addView(instance!!)
            }

        }
    }


    private fun needIgnore(activity: Activity): Boolean {
        val a = activity.javaClass
        return a.isAnnotationPresent(IgnoreCLogView::class.java)
    }

    override fun uncaughtException(p0: Thread, p1: Throwable) {
    }


    companion object{
        private var logTag: String? = null
        @SuppressLint("StaticFieldLeak")
        private var instance: CLog? = null
        private const val logScout:Int = 8
        private const val debuggable = true //正式环境(false)不打印日志，也不能唤起app的debug界面
        private var isAutoScroll = false
        private var logType = LogType.d

        public fun init(app:Application){
            if (debuggable && instance == null) {
                synchronized(CLog::class.java){
                    if (instance == null) {
                        instance = CLog(app.applicationContext)
                        instance!!.initAttr(app)
                    }
                }
            }
        }

        fun v(msg: String) {
            logTag?.let { v(it, msg) }
        }

        fun d(msg: String) {
            logTag?.let { d(it, msg) }
        }

        fun i(msg: String) {
            logTag?.let { i(it, msg) }
        }

        fun w(msg: String) {
            logTag?.let { w(it, msg) }
        }

        fun e(msg: String) {
            logTag?.let { e(it, msg) }
        }

        fun s(msg: String) {
            logTag?.let { s(it, msg) }
        }

        private fun v(tag: String, msg: String) {
            if (instance != null && logType == LogType.v) instance?.print(Log.VERBOSE, tag, msg)
        }

        private fun d(tag: String, msg: String) {
            if (instance != null && logType == LogType.d) instance?.print(Log.DEBUG, tag, msg)
        }

        private fun i(tag: String, msg: String) {
            if (instance != null && logType == LogType.i) instance?.print(Log.INFO, tag, msg)
        }

        private fun w(tag: String, msg: String) {
            if (instance != null && logType == LogType.w) instance?.print(Log.WARN, tag, msg)
        }

        private fun e(tag: String, msg: String) {
            if (instance != null && logType == LogType.e) instance?.print(Log.ERROR, tag, msg)
        }

        private fun s(tag: String, msg: String) {
            if (instance != null && logType == LogType.s) instance?.print(logScout, tag, msg)
        }


    }






}



