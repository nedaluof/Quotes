@file:SuppressLint("NotifyDataSetChanged")

package com.nedaluof.quotes.ui.base

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by NedaluOf on 8/10/2021.
 */
abstract class BaseRecycler<M> : RecyclerView.Adapter<BaseVH>() {

    val items = ArrayList<M>()

    fun addItems(items: List<M>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = items.size
}

abstract class BaseVH(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(position: Int)
}