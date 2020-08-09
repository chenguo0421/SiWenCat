package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.os.Handler
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.router.manager.RouterManager
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.bean.MenuBean
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.portal_item_my_fun_detail.view.*

class MyFunDetailAdapter(
    var context: Context,
    var data: ArrayList<MenuBean>,
    var parentPosition:Int,
    var listener:OnFunDetailItemClickListener
): RecyclerView.Adapter<MyFunDetailAdapter.MyHolder>() {


    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) ,LayoutContainer{
        override var containerView:View = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.portal_item_my_fun_detail,null)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.containerView.img.setImageResource(data[position].resource)
        holder.containerView.tv.text = data[position].name
        holder.containerView.content_cl.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.containerView.img,0.5f,1f,300L)
            Handler().postDelayed(Runnable {
                listener.onFunDetailItemClick(parentPosition,holder.adapterPosition)
            },300L)
        }
    }
    interface OnFunDetailItemClickListener{
        fun onFunDetailItemClick(parentPosition: Int, position: Int)
    }
}