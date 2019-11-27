package cn.com.cg.clog.adapter

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.com.cg.clog.R

/**
 * Discription  {}
 * author  chenguo7
 * Date  2019/9/16 19:10
 */
class CLogAdapter(
    private val context: Context,
    private val mList: ArrayList<String>,
    private val typeValue: Float
) : RecyclerView.Adapter<CLogAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.clog_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("TAG","list.size = ${mList.size}")
        holder.tv?.textSize = typeValue
        holder.tv?.text = Html.fromHtml(mList[position])
    }


    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var mView: View? = null
        var tv:TextView? = null
        init {
            mView = itemView
            if (mView != null) {
                tv = mView?.findViewById(R.id.tv)
            }
        }
    }
}