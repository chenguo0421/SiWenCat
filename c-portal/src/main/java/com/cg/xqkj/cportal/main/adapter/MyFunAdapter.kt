package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.ccommon.utils.DeviceUtils
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.bean.MenuBean
import com.cg.xqkj.cportal.main.bean.MyFunBean
import com.cg.xqkj.cportal.main.bean.MyMenuType
import com.pdog.dimension.dp
import com.pdog.dimension.sp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.portal_item_my_fun.view.*
import kotlinx.android.synthetic.main.portal_item_my_tools.view.img
import kotlinx.android.synthetic.main.portal_item_my_tools.view.tv


class MyFunAdapter(
    var context:Context?,
    var data:ArrayList<MyFunBean>,
    var listener:MyFunDetailAdapter.OnFunDetailItemClickListener
) : RecyclerView.Adapter<MyFunAdapter.MyHolder>(){

    open class MyHolder(itemview: View):RecyclerView.ViewHolder(itemview),LayoutContainer{
        override val containerView: View = itemView
    }

    inner class MyHolderGift(private var itemview: View):MyHolder(itemview),LayoutContainer{
        private var contentRv:RecyclerView? = null
        init {
            contentRv = itemview.content_rv
            reSetRecyclerViewParams()
        }

        private fun reSetRecyclerViewParams() {
            itemview.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                165f.dp.toInt()
            )
        }
    }

    inner class MyHolderCollect(private var interview: View):MyHolder(interview),LayoutContainer{
        private var contentRv:RecyclerView? = null
        init {
            contentRv = interview.content_rv
            reSetRecyclerViewParams()
        }

        private fun reSetRecyclerViewParams() {
            interview.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                135f.dp.toInt()
            )
        }
    }

    inner class MyHolderAction(private var interview: View):MyHolder(interview),LayoutContainer{
        private var contentRv:RecyclerView? = null
        init {
            contentRv = interview.content_rv
            reSetRecyclerViewParams()
        }

        private fun reSetRecyclerViewParams() {
            interview.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                130f.dp.toInt()
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        var holder:MyHolder? = null
        when (viewType) {
            MyMenuType.GIFT.ordinal -> {
                val view:View = LayoutInflater.from(context).inflate(R.layout.portal_item_my_fun, parent,false)
                holder = MyHolderGift(view)
            }
            MyMenuType.COLLECT.ordinal -> {
                val view:View = LayoutInflater.from(context).inflate(R.layout.portal_item_my_fun,parent,false)
                holder = MyHolderCollect(view)
            }
            MyMenuType.ACTION.ordinal -> {
                val view:View = LayoutInflater.from(context).inflate(R.layout.portal_item_my_fun, parent,false)
                holder = MyHolderAction(view)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.containerView.img.setImageResource(data[position].resource)
        holder.containerView.tv.text = data[position].name
        when (holder) {
            is MyHolderGift -> {
                initContentRecyclerView(context,
                    holder.containerView.content_rv,
                    data[position].contentMenu,
                    130f.dp,
                    80f.dp,
                    15f.dp,
                    30f.dp,
                    14f.sp,
                    R.color.common_text_color_666666,
                    holder.adapterPosition)
            }
            is MyHolderCollect -> {
                initContentRecyclerView(context,
                    holder.containerView.content_rv,
                    data[position].contentMenu,
                    100f.dp,
                    45f.dp,
                    30f.dp,
                    19f.dp,
                    12f.sp,
                    R.color.common_text_color_666666,
                    holder.adapterPosition)
            }
            is MyHolderAction -> {
                initContentRecyclerView(context,
                    holder.containerView.content_rv,
                    data[position].contentMenu,
                    95f.dp,
                    50f.dp,
                    10f.dp,
                    13f.dp,
                    14f.sp,
                    R.color.common_text_color_333333,
                    holder.adapterPosition)
            }
        }

    }

    private fun initContentRecyclerView(context: Context?,
                                        contentRv: RecyclerView?,
                                        contentMenu: ArrayList<MenuBean>?,
                                        rvHeight:Float,
                                        imgWH: Float,
                                        topMargin:Float,
                                        borderMargin:Float,
                                        textSize: Float,
                                        textColor: Int,
                                        parentPosition:Int) {
        val margin = (DeviceUtils.getScreenWidth(context!!) - 2 * borderMargin - imgWH * contentMenu?.size!!) / (contentMenu.size - 1)
        val adapter = MyFunDetailAdapter(context, contentMenu,rvHeight,imgWH,topMargin,borderMargin,margin,textSize,textColor,parentPosition,listener)
        val manager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        contentRv?.layoutManager = manager
        contentRv?.adapter = adapter
    }

    fun getItemData(parentPosition: Int): MyFunBean {
        return data[parentPosition]
    }

}