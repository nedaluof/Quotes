@file:SuppressLint("NotifyDataSetChanged")

package com.nedaluof.quotes.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.domain.model.tag.TagModel
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.ItemTagBinding
import com.nedaluof.quotes.util.click

/**
 * Created by NedaluOf on 5/30/2022.
 */
class TagsAdapter(
  private val onTagSelected: (String, Int) -> Unit
) : RecyclerView.Adapter<TagsAdapter.TagVH>() {

  private val tags = ArrayList<TagModel>()

  fun addItems(comingTags: List<TagModel>) {
    with(tags) {
      clear()
      addAll(comingTags)
      notifyDataSetChanged()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TagVH(
    DataBindingUtil.inflate(
      LayoutInflater.from(parent.context),
      R.layout.item_tag, parent, false
    )
  )

  override fun onBindViewHolder(holder: TagVH, position: Int) {
    holder.bind(position)
  }

  override fun getItemCount() = tags.size

  inner class TagVH(
    private val binding: ItemTagBinding
  ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int) {
      with(binding) {
        val tagModel = tags[position]
        tag = tagModel
        root.click {
          onTagSelected(tagModel.name, position)
          tags.first { it.isSelected }.isSelected = false
          tagModel.isSelected = true
          notifyDataSetChanged()
        }
        executePendingBindings()
      }
    }
  }
}