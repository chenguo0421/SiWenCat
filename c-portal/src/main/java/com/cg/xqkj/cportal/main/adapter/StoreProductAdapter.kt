package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.bean.StoreProductsBean
import kotlinx.android.extensions.LayoutContainer

class StoreProductAdapter(var context: Context, var data:ArrayList<StoreProductsBean>) : RecyclerView.Adapter<StoreProductAdapter.MyHolder>(){

    open class MyHolder(itemView: View):RecyclerView.ViewHolder(itemView),LayoutContainer{
        override val containerView: View = itemView
    }

    /**
     * 新人专享
     */
    class HolderForNewcome(itemView: View) : MyHolder(itemView){

    }

    /**
     * 今日推荐
     */
    class HolderForRecommendToday(itemView: View) : MyHolder(itemView){

    }

    /**
     * 品牌精选
     */
    class HolderForChoiceness(itemView: View) : MyHolder(itemView){

    }

    /**
     * 精品推荐
     */
    class HolderForRecommendBoutique(itemView: View) : MyHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val  inflater = LayoutInflater.from(context)
        return when (viewType) {
            0 -> {
                HolderForNewcome(inflater.inflate(R.layout.portal_item_store_product_newcomme,parent,false))
            }
            1 -> {
                HolderForNewcome(inflater.inflate(R.layout.portal_item_store_product_recommendtoday,parent,false))
            }
            2 -> {
                HolderForNewcome(inflater.inflate(R.layout.portal_item_store_product_choiceness,parent,false))
            }
            else -> {
                HolderForNewcome(inflater.inflate(R.layout.portal_item_store_product_recommoendboutique,parent,false))
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type!!
    }
}