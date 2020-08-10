package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.bean.GiftDJYPBean
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.portal_item_gift_type.view.*
import kotlinx.android.synthetic.main.portal_item_gift_type.view.tv_typeDis
import kotlinx.android.synthetic.main.portal_item_gift_type.view.iv_item1
import kotlinx.android.synthetic.main.portal_item_gift_type.view.iv_item2
import kotlinx.android.synthetic.main.portal_item_gift_type_iv.view.*



/**
 *  author : chenguo
 *  date : 2020/8/10 21:33
 *  description : { 请添加该类的描述 }
 */
class GiftDJYPAdapter(
    var context: Context,
    var list:ArrayList<GiftDJYPBean.InnerItem>): RecyclerView.Adapter<GiftDJYPAdapter.MyHolder>() {



    open class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , LayoutContainer {
        override val containerView = itemView
    }

    /**
     * 类型为图片
     */
    class GiftIVTypeHolder(itemView:View) : GiftDJYPAdapter.MyHolder(itemView){
    }

    /**
     * 类型为文字
     */
    class GiftTVTypeHolder(itemView:View) : GiftDJYPAdapter.MyHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        if (viewType == 1){
            return GiftIVTypeHolder(LayoutInflater.from(context).inflate(R.layout.portal_item_gift_type_iv,null))
        }
        return GiftTVTypeHolder(LayoutInflater.from(context).inflate(R.layout.portal_item_gift_type,null))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        when(holder){
            is GiftIVTypeHolder -> {
                Glide.with(context).load(list[position].title).into(holder.itemView.iv_typeName)
                holder.itemView.tv_typeDis.text = list[position].hotText
                Glide.with(context).load(list[position]?.url?.get(0)).into(holder.itemView.iv_item1)
                Glide.with(context).load(list[position]?.url?.get(1)).into(holder.itemView.iv_item2)
            }
            is GiftTVTypeHolder -> {
                holder.itemView.tv_typeName.text = list[position].title
                holder.itemView.tv_typeDis.text = list[position].hotText
                Glide.with(context).load(list[position]?.url?.get(0)).into(holder.itemView.iv_item1)
                Glide.with(context).load(list[position]?.url?.get(1)).into(holder.itemView.iv_item2)
            }
        }

    }

}