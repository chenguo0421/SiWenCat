package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.ccommon.utils.DeviceUtils
import com.bumptech.glide.Glide
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.bean.GiftDJYPBean
import com.pdog.dimension.dp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.portal_item_product_i.view.*

/**
 *  author : chenguo
 *  date : 2020/8/12 21:55
 *  description : { 请添加该类的描述 }
 */
class GiftItemAdapter(
    var context: Context,
    var data:ArrayList<GiftDJYPBean.GiftTypeItem.GiftItem>
): RecyclerView.Adapter<GiftItemAdapter.MyHolder>(){


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , LayoutContainer {
        override val containerView = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.portal_item_product_i,null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val layoutParams = holder.itemView.iv_content.layoutParams
        layoutParams.width = (DeviceUtils.getScreenWidth(context) - 25.dp) / 2
        holder.itemView.iv_content.layoutParams = layoutParams
        Glide.with(context).load(data?.get(position)?.url?.get(0)).into(holder.itemView.iv_content)
        holder.itemView.tv_name.text = data?.get(position)?.name ?: ""
        holder.itemView.tv_price.text = data?.get(position)?.price ?: ""
        holder.itemView.tv_num_convert.text = String.format(context.resources.getString(R.string.portal_store_item_convertnum) ,data?.get(position)?.convertNum ?: "")
    }

    fun updateData(list: ArrayList<GiftDJYPBean.GiftTypeItem.GiftItem>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
}