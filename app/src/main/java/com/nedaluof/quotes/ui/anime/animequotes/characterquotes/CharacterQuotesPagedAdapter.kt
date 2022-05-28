package com.nedaluof.quotes.ui.anime.animequotes.characterquotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.data.datasource.remote.apiresponse.anime.Quote
import com.nedaluof.quotes.databinding.ItemAnimeQuoteBinding

/**
 * Created by NedaluOf on 8/12/2021.
 */
class CharacterQuotesPagedAdapter :
    PagingDataAdapter<Quote, CharacterQuotesPagedAdapter.AnimeQuoteVH>(CharacterQuoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AnimeQuoteVH(
        ItemAnimeQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: AnimeQuoteVH, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class AnimeQuoteVH(
        private val binding: ItemAnimeQuoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quoteObj: Quote) {
            binding.run {
                quote = quoteObj
                executePendingBindings()
                characterLyt.isVisible = false
            }
        }
    }

    private class CharacterQuoteDiffCallback : DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote) =
            oldItem.quote == newItem.quote

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote) =
            oldItem == newItem
    }
}