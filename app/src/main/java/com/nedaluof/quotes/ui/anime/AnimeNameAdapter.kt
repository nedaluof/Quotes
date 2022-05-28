package com.nedaluof.quotes.ui.anime

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nedaluof.quotes.databinding.ItemAnimeNameBinding
import com.nedaluof.quotes.ui.base.BaseRecycler
import com.nedaluof.quotes.ui.base.BaseVH
import com.nedaluof.quotes.util.generateRandomColor

/**
 * Created by NedaluOf on 8/10/2021.
 */
class AnimeNameAdapter(
    val onItemClick: (String) -> Unit
) : BaseRecycler<String>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AnimeNameVH(
            ItemAnimeNameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    inner class AnimeNameVH(
        private val binding: ItemAnimeNameBinding
    ) : BaseVH(binding.root) {
        override fun bind(position: Int) {
            binding.run {
                val generatedColors = generateRandomColor()
                root.setBackgroundColor(generatedColors[1])
                root.setOnClickListener { onItemClick(items[position]) }
                animeName.setTextColor(generatedColors[0])
                name = items[position]
                executePendingBindings()
            }
        }
    }
}