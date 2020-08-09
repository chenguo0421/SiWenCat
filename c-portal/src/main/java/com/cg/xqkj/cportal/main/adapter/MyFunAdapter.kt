package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
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
    private var listener:MyFunDetailAdapter.OnFunDetailItemClickListener
) : RecyclerView.Adapter<MyFunAdapter.MyHolder>(){

    open class MyHolder(itemview: View):RecyclerView.ViewHolder(itemview),LayoutContainer{
        override val containerView: View = itemView
        public var bottom_line_view:View = itemview.bottom_line_view

    }

    inner class MyHolderGift(private var itemview: View):MyHolder(itemview),LayoutContainer{
        private var contentRv:RecyclerView? = null
        init {
            contentRv = itemview.content_rv
        }
    }

    inner class MyHolderCollect(private var interview: View):MyHolder(interview),LayoutContainer{
        private var contentRv:RecyclerView? = null
        init {
            contentRv = interview.content_rv
        }
    }

    inner class MyHolderAction(private var interview: View):MyHolder(interview),LayoutContainer{
        private var contentRv:RecyclerView? = null
        init {
            contentRv = interview.content_rv
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        var holder:MyHolder? = null
        when (viewType) {
            MyMenuType.GIFT.ordinal -> {
                val view:View = LayoutInflater.from(context).inflate(R.layout.portal_item_my_fun, null)
                holder = MyHolderGift(view)
            }
            MyMenuType.COLLECT.ordinal -> {
                val view:View = LayoutInflater.from(context).inflate(R.layout.portal_item_my_fun,null)
                holder = MyHolderCollect(view)
            }
            MyMenuType.ACTION.ordinal -> {
                val view:View = LayoutInflater.from(context).inflate(R.layout.portal_item_my_fun, null)
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
                    3,
                    holder.adapterPosition)
            }
            is MyHolderCollect -> {
                initContentRecyclerView(context,
                    holder.containerView.content_rv,
                    data[position].contentMenu,
                    5,
                    holder.adapterPosition)
            }
            is MyHolderAction -> {
                initContentRecyclerView(context,
                    holder.containerView.content_rv,
                    data[position].contentMenu,
                    5,
                    holder.adapterPosition)
            }
        }

        if (position == data.size - 1) {
            holder.bottom_line_view.visibility = View.INVISIBLE
        }else{
            holder.bottom_line_view.visibility = View.VISIBLE
        }


    }

    private fun initContentRecyclerView(context: Context?,
                                        contentRv: RecyclerView?,
                                        contentMenu: ArrayList<MenuBean>?,
                                        spanCount:Int,
                                        parentPosition:Int) {
        val adapter = MyFunDetailAdapter(context!!, contentMenu!!,parentPosition,listener)
        val manager = GridLayoutManager(context,spanCount,GridLayoutManager.VERTICAL,false)
        contentRv?.layoutManager = manager
        contentRv?.adapter = adapter
    }

    fun getItemData(parentPosition: Int): MyFunBean {
        return data[parentPosition]
    }

}