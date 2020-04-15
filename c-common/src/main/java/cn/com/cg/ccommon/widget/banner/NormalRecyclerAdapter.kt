package cn.com.cg.ccommon.widget.banner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer

class NormalRecyclerAdapter(
    var context: Context,
    private var urlList: List<String>,
    private var onBannerItemClickListener: RecyclerViewBannerBase.OnBannerItemClickListener
): RecyclerView.Adapter<NormalRecyclerAdapter.NormalHolder>() {


    override fun onBindViewHolder(holder: NormalHolder, position: Int) {
        if (urlList.isEmpty()) return
        val url = urlList[position % urlList.size]
        val img = holder.itemView as ImageView
        Glide.with(context).load(url).into(img)
        img.setOnClickListener {
            onBannerItemClickListener!!.onItemClick(position % urlList!!.size)
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView),LayoutContainer {
        override val containerView: View = itemView
        init {
            val  bannerItem = itemView as AppCompatImageView
            val params: RecyclerView.LayoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            bannerItem.layoutParams = params
            bannerItem.scaleType = ImageView.ScaleType.FIT_XY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalHolder {
        return NormalHolder(AppCompatImageView(context))
    }
}