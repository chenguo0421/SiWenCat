package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.ccommon.widget.banner.BannerLayout
import cn.com.cg.ccommon.widget.banner.CardBannerAdapter
import cn.com.cg.ccommon.widget.itemdecoration.RecycleGridDivider
import cn.com.cg.ccommon.widget.recyclerview.NoScrollGridLayoutManager
import cn.com.cg.ccommon.widget.recyclerview.NoScrollLinearLayoutManager
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.bean.HomeBean
import com.cg.xqkj.cportal.main.bean.StoryBean
import com.pdog.dimension.dp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.portal_item_home_graphic.view.*
import kotlinx.android.synthetic.main.portal_item_home_store.view.*
import kotlinx.android.synthetic.main.portal_item_home_video.view.*
import kotlinx.android.synthetic.main.portal_item_home_video.view.title

/**
 *  author : chenguo
 *  date : 2020/5/13
 *  description : {  }
 */
class HomeListAdapter(var context: Context, var data:ArrayList<HomeBean.InnerItem>) : RecyclerView.Adapter<HomeListAdapter.MyHolder>(){

    open class MyHolder(itemView: View):RecyclerView.ViewHolder(itemView),LayoutContainer{
        override val containerView = itemView
    }

    /**
     * 最嗨一刻
     */
    class ZVideoHolder(itemView:View) : MyHolder(itemView){
    }

    /**
     * 最小说
     */
    class ZStoryHolder(itemView:View) : MyHolder(itemView){
    }

    /**
     * 最心声
     */
    class ZAudioHolder(itemView:View) : MyHolder(itemView){
    }

    /**
     * 最新动态
     */
    class ZGraphicHolder(itemView:View) : MyHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val  inflater = LayoutInflater.from(context)
        return when (viewType) {
            0 -> {
                ZVideoHolder(inflater.inflate(R.layout.portal_item_home_video,parent,false))
            }
            1 -> {
                ZStoryHolder(inflater.inflate(R.layout.portal_item_home_store,parent,false))
            }
            2 -> {
                ZAudioHolder(inflater.inflate(R.layout.portal_item_home_store,parent,false))
            }
            3 -> {
                ZGraphicHolder(inflater.inflate(R.layout.portal_item_home_graphic,parent,false))
            }
            else -> {
                ZVideoHolder(inflater.inflate(R.layout.portal_item_home_video,parent,false))
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        when(holder){
            is ZVideoHolder ->{
                setZVideoItem(holder,position)
            }
            is ZStoryHolder ->{
                setZStoryItem(holder,position)
            }
            is ZAudioHolder ->{
                setZAudioItem(holder,position)
            }
            is ZGraphicHolder -> {
                setZGraphicItem(holder,position)
            }
        }
    }

    private fun setZGraphicItem(holder: HomeListAdapter.ZGraphicHolder, position: Int) {
        holder.itemView.tv_title.text = data[position].title
        data[position].list?.let {
            val gridLayoutManager = NoScrollLinearLayoutManager(context)
            val adapter = HomeGraphicAdapter(context,data[position].list!!)
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.common_rv_divider_f2f2f2_ten)!!)
            holder.itemView.rv_graphic.layoutManager = gridLayoutManager
            holder.itemView.rv_graphic.adapter = adapter
            holder.itemView.rv_graphic.addItemDecoration(divider)
        }
    }

    private fun setZAudioItem(holder: HomeListAdapter.ZAudioHolder, position: Int) {
        holder.itemView.title.text =  data[position].title
        data[position].list?.let {
            val gridLayoutManager = NoScrollGridLayoutManager(context,3)
            val adapter = HomeAudioAdapter(context,data[position].list!!)
            holder.itemView.rv_store.layoutManager = gridLayoutManager
            holder.itemView.rv_store.adapter = adapter
            holder.itemView.rv_store.addItemDecoration(RecycleGridDivider(20.dp))
        }
    }

    private fun setZStoryItem(holder: HomeListAdapter.ZStoryHolder, position: Int) {
        holder.itemView.title.text =  data[position].title
        data[position].list?.let {
            val linearLayoutManager = NoScrollLinearLayoutManager(context)
            val adapter = HomeStoryAdapter(context,data[position].list!!)
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.common_rv_divider_ffffff_fifty)!!)
            holder.itemView.rv_store.addItemDecoration(divider)
            holder.itemView.rv_store.layoutManager = linearLayoutManager
            holder.itemView.rv_store.adapter = adapter
        }
    }



    private fun setZVideoItem(holder: HomeListAdapter.ZVideoHolder, position: Int) {
        holder.itemView.title.text =  data[position].title
        val videoImgList = reBuildVideoImgList(data[position].list)
        val adapter = CardBannerAdapter(context,videoImgList)
        adapter.setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener {
            ToastUtils.show("点击了：" + data[position].list?.get(it)?.title)
        })
        holder.itemView.banner.setAdapter(adapter)
    }

    private fun reBuildVideoImgList(list: ArrayList<StoryBean>?): ArrayList<String> {
        val videos = ArrayList<String>()
        if (list == null || list.isEmpty()) return videos
        for (item in list){
            item.image?.let { videos.add(it) }
        }
        return videos
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type!!
    }
}