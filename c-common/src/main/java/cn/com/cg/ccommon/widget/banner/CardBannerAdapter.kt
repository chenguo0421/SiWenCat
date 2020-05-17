package cn.com.cg.ccommon.widget.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.ccommon.R
import cn.com.cg.ccommon.utils.ToastUtils
import cn.com.cg.router.manager.RouterManager
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.common_card_item_image.*
import kotlinx.android.synthetic.main.common_card_item_image.view.*

/**
 *  author : chenguo
 *  date : 2020/5/14
 *  description : {  }
 */
class CardBannerAdapter(
    var context: Context,
    var data:ArrayList<String>,
    var resource:Int
) : RecyclerView.Adapter<CardBannerAdapter.MyHolder>(){

    private var onBannerItemClickListener: BannerLayout.OnBannerItemClickListener? = null

    fun setOnBannerItemClickListener(onBannerItemClickListener: BannerLayout.OnBannerItemClickListener?) {
        this.onBannerItemClickListener = onBannerItemClickListener
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView),LayoutContainer{
        override val containerView = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return if (resource == 0 ){
            MyHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(R.layout.common_card_item_image, parent, false))
        }else{
            MyHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(resource, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (data.isEmpty()) return
        val index: Int = position % data.size
        val url: String = data[index]
        val img = holder.itemView.image as AppCompatImageView
        Glide.with(context).load(url).into(img)
        img.setOnClickListener {
            RouterManager.getInstance().with(context).action("/AnimUtils/alphaView").callMethod(holder.itemView.image,0.5f,1f,300L)
            if (onBannerItemClickListener != null) {
                onBannerItemClickListener!!.onItemClick(index)
            }
        }
    }

}