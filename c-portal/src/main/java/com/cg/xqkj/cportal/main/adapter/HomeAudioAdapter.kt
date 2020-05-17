package com.cg.xqkj.cportal.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cg.xqkj.cportal.R
import com.cg.xqkj.cportal.main.bean.StoryBean
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.portal_grid_item_audio.view.*

/**
 *  author : chenguo
 *  date : 2020/5/17
 *  description : {  }
 */
class HomeAudioAdapter (
    var context: Context,
    var data:ArrayList<StoryBean>
) : RecyclerView.Adapter<HomeAudioAdapter.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        override val containerView = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.portal_grid_item_audio,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(context).load(data[position].image).into(holder.itemView.iv_audio)
        holder.itemView.tv_title.text = data[position].title
        holder.itemView.tv_dis.text = data[position].intro
    }
}