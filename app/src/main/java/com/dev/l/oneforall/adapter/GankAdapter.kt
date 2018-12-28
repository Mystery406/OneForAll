package com.dev.l.oneforall.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dev.l.oneforall.R
import com.dev.l.oneforall.entity.gank.Gank
import com.dev.l.oneforall.widget.RatioImageView
import kotlinx.android.synthetic.main.item_gank.view.*

class GankAdapter(private val context: Context) : RecyclerView.Adapter<GankAdapter.ViewHolder>() {
    lateinit var gankList: ArrayList<Gank>
    lateinit var onGankClickListener: OnGankClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_gank, parent, false))


    override fun getItemCount(): Int = gankList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivGank.setOriginalSize(50, 50)
        holder.ivGank.setOnClickListener {
            onGankClickListener.onGankClick(position)
        }
        Glide.with(context)
                .load(gankList[position].url)
                .apply(RequestOptions().centerCrop())
                .into(holder.ivGank)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivGank: RatioImageView = itemView.ivGank
    }

    interface OnGankClickListener {
        fun onGankClick(position: Int)
    }
}