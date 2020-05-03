package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.ccommon.utils.DeviceUtils
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.bean.StoreProductsBean
import com.pdog.dimension.dp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.portal_item_store_product_newcomme.view.*

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
                HolderForRecommendToday(inflater.inflate(R.layout.portal_item_store_product_recommendtoday,parent,false))
            }
            2 -> {
                HolderForRecommendBoutique(inflater.inflate(R.layout.portal_item_store_product_choiceness,parent,false))
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
        when(holder){
            is HolderForNewcome ->{
                setNewcomeItem(holder,position)
            }
            is HolderForRecommendToday ->{
                setRecommendTodayItem(holder,position)
            }
            is HolderForRecommendBoutique ->{

            }
        }

    }

    private fun setRecommendTodayItem(holder: StoreProductAdapter.HolderForRecommendToday, position: Int) {

    }

    private fun setNewcomeItem(holder: StoreProductAdapter.HolderForNewcome, position: Int) {
        holder.itemView.title_lv.text = data[position].title?.get(0)?.title
        holder.itemView.title_lv.setTextColor(Color.parseColor(data[position].title?.get(0)?.titleColor))

        holder.itemView.hot_rtc.setContentText(data[position].title?.get(0)?.hotText)
        holder.itemView.hot_rtc.setContentTextColor(Color.parseColor(data[position].title?.get(0)?.hotTextColor))
        holder.itemView.hot_rtc.setContentBackgroundColor(Color.parseColor(data[position].title?.get(0)?.hotTextBGColor))
        holder.itemView.hot_rtc.invalidate()

        holder.itemView.next_tv.text = data[position].title?.get(0)?.nextText
        holder.itemView.next_tv.setTextColor(Color.parseColor(data[position].title?.get(0)?.nextTextColor))

        val linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        holder.itemView.rv_item.layoutManager = linearLayoutManager
        val adapter = TypeNewcommeAdapter(context,data[position].products,getNewComeItemWidth(),10.dp,10.dp)
        holder.itemView.rv_item.adapter = adapter
    }

    private fun getNewComeItemWidth():Int{
        return (DeviceUtils.getScreenWidth(context) - 2 * 10.dp - 4 * 10.dp) / 3
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type!!
    }
}