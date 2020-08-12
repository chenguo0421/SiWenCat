package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.intf.OnItemClickListener
import com.cg.xqkj.cportal.main.bean.GiftDJYPBean
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.portal_item_gift_product_type.view.*

/**
 *  author : chenguo
 *  date : 2020/8/12 21:24
 *  description : { 请添加该类的描述 }
 */
class GiftTypeAdapter(
    var context:Context,
    var data:ArrayList<GiftDJYPBean.GiftTypeItem>,
    var listener: OnItemClickListener<GiftDJYPBean.GiftTypeItem>
):RecyclerView.Adapter<GiftTypeAdapter.MyHolder>(){

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , LayoutContainer {
        override val containerView = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.portal_item_gift_product_type,null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.tv_type.text = data[position].giftTypeText
        holder.itemView.tv_typeDis.text = data[position].giftTypeTextDis
        if (data[position].isSelect){
            holder.itemView.iv_select.visibility = View.VISIBLE
        }else{
            holder.itemView.iv_select.visibility = View.INVISIBLE
        }

        holder.itemView.cl_content.setOnClickListener {
            listener.onItemClick(data[holder.adapterPosition],holder.adapterPosition)
        }
    }

    fun setCurrentSelect(i: Int) {
        if (i >= 0 && i < data.size) {
            for (item in data){
                item.isSelect = false
            }
            data[i].isSelect = true
            notifyDataSetChanged()
        }
    }

    fun getChildList(i: Int): ArrayList<GiftDJYPBean.GiftTypeItem.GiftItem>?{
        if (i >= 0 && i < data.size) {
            return data[i].gifts
        }
        return null
    }

    fun updateData(giftList: ArrayList<GiftDJYPBean.GiftTypeItem>) {
        data.clear()
        data.addAll(giftList)
        notifyDataSetChanged()
    }
}