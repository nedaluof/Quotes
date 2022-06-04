package com.nedaluof.quotes.ui.main.authorquotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.domain.model.quote.QuoteModel
import com.nedaluof.quotes.databinding.ItemQuoteBinding

/**
 * Created by NedaluOf on 8/11/2021.
 */
class AuthorQuotesPagedAdapter :
    PagingDataAdapter<QuoteModel, AuthorQuotesPagedAdapter.AuthorQuoteVH>(
        AuthorQuoteDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AuthorQuoteVH(
        ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AuthorQuoteVH, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class AuthorQuoteVH(
        private val binding: ItemQuoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quoteObj: QuoteModel) {
            binding.run {
                quote = quoteObj
                executePendingBindings()
                author.isVisible = false
            }
        }
    }

    private class AuthorQuoteDiffCallback : DiffUtil.ItemCallback<QuoteModel>() {
        override fun areItemsTheSame(oldItem: QuoteModel, newItem: QuoteModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: QuoteModel, newItem: QuoteModel) =
            oldItem == newItem
    }
}