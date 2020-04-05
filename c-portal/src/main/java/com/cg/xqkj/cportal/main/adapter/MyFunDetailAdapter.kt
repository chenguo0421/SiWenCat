package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.bean.MenuBean
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.portal_item_my_fun_detail.view.*

class MyFunDetailAdapter(
    var context: Context?,
    var data: ArrayList<MenuBean>,
    var rvHeight:Float,
    var imgWH: Float,
    var topMargin:Float,
    var firstLeftMargin:Float,
    var leftMargin:Float,
    var textSize: Float,
    var textColor: Int
): RecyclerView.Adapter<MyFunDetailAdapter.MyHolder>() {


    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) ,LayoutContainer{
        override var containerView:View = itemView
        init {
            reSetRecyclerViewParams()
        }
        private fun reSetRecyclerViewParams() {
            var params = ConstraintLayout.LayoutParams(imgWH.toInt(), rvHeight.toInt())
            params.topMargin = topMargin.toInt()
            containerView.layoutParams = params
            containerView.tv.setTextColor(textColor)
            containerView.tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.portal_item_my_fun_detail,null)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        reSetViewsParams(holder.containerView,holder.adapterPosition)
        holder.containerView.img.setImageResource(data[position].resource)
        holder.containerView.tv.text = data[position].name
    }

    private fun reSetViewsParams(containerView: View, adapterPosition: Int) {
        var imgParams = containerView.img.layoutParams as ConstraintLayout.LayoutParams
        imgParams.width = imgWH.toInt()
        imgParams.height = imgWH.toInt()
        containerView.img.layoutParams = imgParams

        var params = containerView.layoutParams as ConstraintLayout.LayoutParams
        params.leftMargin = if (adapterPosition == 0) firstLeftMargin.toInt() else leftMargin.toInt()
        containerView.layoutParams = params
    }
}