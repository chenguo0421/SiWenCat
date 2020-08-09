package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.ccommon.utils.DeviceUtils
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.ccommon.widget.itemdecoration.RecycleGridDivider
import cn.com.cg.ccommon.widget.recyclerview.NoScrollGridLayoutManager
import cn.com.cg.router.manager.RouterManager
import com.bumptech.glide.Glide
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.bean.StoreProductsBean
import com.pdog.dimension.dp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.portal_item_store_product_choiceness.view.tv_subtitle
import kotlinx.android.synthetic.main.portal_item_store_product_choiceness.view.tv_title
import kotlinx.android.synthetic.main.portal_item_store_product_newcomme.view.*
import kotlinx.android.synthetic.main.portal_item_store_product_recommendtoday.view.*
import kotlinx.android.synthetic.main.portal_item_store_product_recommendtoday.view.iv_l1r1
import kotlinx.android.synthetic.main.portal_item_store_product_recommendtoday.view.iv_l2r1
import kotlinx.android.synthetic.main.portal_item_store_product_recommendtoday.view.iv_l2r2
import kotlinx.android.synthetic.main.portal_item_store_product_recommoendboutique.view.*

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
                HolderForChoiceness(inflater.inflate(R.layout.portal_item_store_product_choiceness,parent,false))
            }
            3 -> {
                HolderForRecommendBoutique(inflater.inflate(R.layout.portal_item_store_product_recommoendboutique,parent,false))
            }
            else -> {
                HolderForNewcome(inflater.inflate(R.layout.portal_item_store_product_newcomme,parent,false))
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
            is HolderForChoiceness ->{
                setChoicenessItem(holder,position)
            }
            is HolderForRecommendBoutique -> {
                setRecommendBoutiqueItem(holder,position)
            }
        }

    }

    private fun setRecommendBoutiqueItem(holder: StoreProductAdapter.HolderForRecommendBoutique, position: Int) {
        holder.itemView.tv_title.text = data[position].title?.get(0)?.title
        holder.itemView.tv_title.setTextColor(Color.parseColor(data[position].title?.get(0)?.titleColor))

        holder.itemView.tv_subtitle.text = data[position].title?.get(0)?.nextText
        holder.itemView.tv_subtitle.setTextColor(Color.parseColor(data[position].title?.get(0)?.nextTextColor))

        val params = holder.itemView.layoutParams
        var size = data[position].products?.size
        if (size?.rem(2) != 0){
            size = size?.plus(1)
        }
        val imgHeight = ((DeviceUtils.getScreenWidth(context) - 25.dp) / 2 ) * 190 / 175
        val gridItemHeight = imgHeight + 80.dp
        params.height = ((size?.div(2)?.times(gridItemHeight)!!) + (25 + 8).dp + (size.div(2)
            .times(5.dp))) + 5.dp

        holder.itemView.layoutParams = params

        val gridLayoutManager= NoScrollGridLayoutManager(context,2)
        gridLayoutManager.setScrollEnabled(false)
        holder.itemView.rv_product.layoutManager = gridLayoutManager
        val adapter = RecommendBoutiqueAdapter(context,data[position].products)
        holder.itemView.rv_product.adapter = adapter
        holder.itemView.rv_product.addItemDecoration(RecycleGridDivider(5.dp))
    }

    private fun setChoicenessItem(holder: StoreProductAdapter.HolderForChoiceness, position: Int) {
        holder.itemView.tv_title.text = data[position].title?.get(0)?.title
        holder.itemView.tv_title.setTextColor(Color.parseColor(data[position].title?.get(0)?.titleColor))

        holder.itemView.tv_subtitle.text = data[position].title?.get(0)?.nextText
        holder.itemView.tv_subtitle.setTextColor(Color.parseColor(data[position].title?.get(0)?.nextTextColor))

        Glide.with(context).load(data[position].products?.get(0)?.url?.get(0)).into(holder.itemView.iv_l1r1)
        Glide.with(context).load(data[position].products?.get(1)?.url?.get(0)).into(holder.itemView.iv_l2r1)
        Glide.with(context).load(data[position].products?.get(2)?.url?.get(0)).into(holder.itemView.iv_l2r2)

        holder.itemView.iv_l1r1.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l1r1,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(0)?.productTypeName)
        }

        holder.itemView.iv_l2r1.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l2r1,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(1)?.productTypeName)
        }

        holder.itemView.iv_l2r2.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l2r2,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(2)?.productTypeName)
        }
    }

    private fun setRecommendTodayItem(holder: StoreProductAdapter.HolderForRecommendToday, position: Int) {
        holder.itemView.lv_title.text = data[position].title?.get(0)?.title
        holder.itemView.lv_title.setTextColor(Color.parseColor(data[position].title?.get(0)?.titleColor))

        holder.itemView.lv_title1.text = data[position].title?.get(1)?.title
        holder.itemView.lv_title1.setTextColor(Color.parseColor(data[position].title?.get(1)?.titleColor))

        holder.itemView.rtc_hot.setContentText(data[position].title?.get(0)?.hotText)
        holder.itemView.rtc_hot.setContentTextColor(Color.parseColor(data[position].title?.get(0)?.hotTextColor))
        holder.itemView.rtc_hot.setContentBackgroundColor(Color.parseColor(data[position].title?.get(0)?.hotTextBGColor))
        holder.itemView.rtc_hot.invalidate()

        holder.itemView.rtc_hot1.setContentText(data[position].title?.get(1)?.hotText)
        holder.itemView.rtc_hot1.setContentTextColor(Color.parseColor(data[position].title?.get(1)?.hotTextColor))
        holder.itemView.rtc_hot1.setContentBackgroundColor(Color.parseColor(data[position].title?.get(1)?.hotTextBGColor))
        holder.itemView.rtc_hot1.invalidate()

        holder.itemView.tv_l1r1_adv.text = data[position].products?.get(0)?.advText
        holder.itemView.tv_l1r2_adv.text = data[position].products?.get(1)?.advText

        holder.itemView.tv_title_l2r1.text = data[position].products?.get(2)?.productTypeName
        holder.itemView.tv_title_l2r2.text = data[position].products?.get(3)?.productTypeName
        holder.itemView.tv_title_l2r3.text = data[position].products?.get(4)?.productTypeName
        holder.itemView.tv_title_l2r4.text = data[position].products?.get(5)?.productTypeName

        holder.itemView.tv_title_adv_l2r1.text = data[position].products?.get(2)?.advText
        holder.itemView.tv_title_adv_l2r2.text = data[position].products?.get(3)?.advText
        holder.itemView.tv_title_adv_l2r3.text = data[position].products?.get(4)?.advText
        holder.itemView.tv_title_adv_l2r4.text = data[position].products?.get(5)?.advText

        Glide.with(context).load(data[position].products?.get(0)?.url?.get(0)).into(holder.itemView.iv_l1r1)
        Glide.with(context).load(data[position].products?.get(0)?.url?.get(1)).into(holder.itemView.iv_l1r2)
        Glide.with(context).load(data[position].products?.get(1)?.url?.get(0)).into(holder.itemView.iv_l1r3)
        Glide.with(context).load(data[position].products?.get(1)?.url?.get(1)).into(holder.itemView.iv_l1r4)

        Glide.with(context).load(data[position].products?.get(2)?.url?.get(0)).into(holder.itemView.iv_l2r1)
        Glide.with(context).load(data[position].products?.get(3)?.url?.get(0)).into(holder.itemView.iv_l2r2)
        Glide.with(context).load(data[position].products?.get(4)?.url?.get(0)).into(holder.itemView.iv_l2r3)
        Glide.with(context).load(data[position].products?.get(5)?.url?.get(0)).into(holder.itemView.iv_l2r4)

        holder.itemView.iv_l1r1.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l1r1,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(0)?.productTypeName)
        }
        holder.itemView.iv_l1r2.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l1r2,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(0)?.productTypeName)
        }

        holder.itemView.iv_l1r3.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l1r3,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(1)?.productTypeName)
        }
        holder.itemView.iv_l1r4.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l1r4,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(1)?.productTypeName)
        }

        holder.itemView.iv_l2r1.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l2r1,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(2)?.productTypeName)
        }
        holder.itemView.iv_l2r2.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l2r2,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(3)?.productTypeName)
        }
        holder.itemView.iv_l2r3.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l2r3,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(4)?.productTypeName)
        }
        holder.itemView.iv_l2r4.setOnClickListener{
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.iv_l2r4,0.5f,1f,300L)
            ToastUtils.show("点击了：" + data[position].products?.get(5)?.productTypeName)
        }

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