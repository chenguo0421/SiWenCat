package cn.com.cg.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import cn.com.cg.mvp.base.BasePresenter
import cn.com.cg.mvp.base.intf.BaseView
import cn.com.cg.router.manager.path.RouterBeanManager
import com.trello.rxlifecycle2.components.support.RxDialogFragment
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import cn.com.cg.base.intf.EnterAnimType
import cn.com.cg.router.R
import cn.com.cg.router.manager.RouterManager
import kotlinx.android.synthetic.main.include_base_header.*


/**
 *  author : chenguo
 *  date : 2019/11/30 18:58
 *  description : { 请添加该类的描述 }
 */
abstract class BaseDialogFragment<V: BaseView,P: BasePresenter<V>> : RxDialogFragment() {
    open var fragmentTag:String? = ""
    private var mView: V? = null
    private var mPresenter: P? = null
    private var statusBarView: View? = null
    private var orientation:EnterAnimType? = EnterAnimType.RIGHT_TO_LEFT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (mPresenter == null) {
            mPresenter = createPresenter()
        }

        if (mView == null) {
            mView = createView()
        }

        mPresenter?.attachView(mView!!)

        orientation = isEnterAnimSlideToUp()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val v = inflater.inflate(initLayoutId(), container,false)
        initHeaderView(v)
        animByOrientation(v)
        return v
    }

    private fun animByOrientation(v:View) {
        when (orientation) {
            EnterAnimType.RIGHT_TO_LEFT -> RouterManager.getInstance()
                .with(activity!!)
                .action("/AnimUtils/rightToLeft")
                .callMethod(v)
            EnterAnimType.LEFT_TO_RIGHT -> RouterManager.getInstance()
                .with(activity!!)
                .action("/AnimUtils/leftToRight")
                .callMethod(v)
            EnterAnimType.SLIDE_TO_UP -> RouterManager.getInstance()
                .with(activity!!)
                .action("/AnimUtils/slideToUp")
                .callMethod(v)
            EnterAnimType.UP_TO_SLIDE -> RouterManager.getInstance()
                .with(activity!!)
                .action("/AnimUtils/upToSlide")
                .callMethod(v)
        }
    }

    private fun initHeaderView(view:View) {
        val back_img = view.findViewById<ImageView>(R.id.back_img)
        back_img?.setOnClickListener(View.OnClickListener {
            if (dialog!!.isShowing){
                dismiss()
            }
        })
    }

    public fun setHeaderTitle(title:String){
        title_tv?.text = title
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RouterBeanManager.getInstance().registerFM(this)
        initData()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFFFF")))
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if(isAdded){
            return
        }
        super.show(manager, tag)
    }

    override fun showNow(manager: FragmentManager, tag: String?) {
        if(isAdded){
            return
        }
        super.showNow(manager, tag)
    }

    abstract fun createPresenter(): P
    abstract fun createView(): V
    protected abstract fun initLayoutId(): Int
    abstract fun initData()
    abstract fun initListener()
    abstract fun getInstance():BaseDialogFragment<V,P>
    abstract fun isEnterAnimSlideToUp(): EnterAnimType


    override fun onDestroy() {
        RouterBeanManager.getInstance().unRegisterFM(this)
        mPresenter?.detachView()
        super.onDestroy()
    }
}