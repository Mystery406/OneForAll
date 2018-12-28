package com.dev.l.oneforall.callback

import android.support.v7.util.DiffUtil
import com.dev.l.oneforall.entity.gank.Gank

class GankDiffCallback(var oldList: ArrayList<Gank>, var newList: ArrayList<Gank>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition]._id == newList[newItemPosition]._id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].url == newList[newItemPosition].url
}