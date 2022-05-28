package com.nedaluof.quotes.ui.inspirational

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.domain.model.QuoteModel
import com.nedaluof.quotes.databinding.ItemInspirationalQuoteBinding
import com.nedaluof.quotes.util.bindingadapter.ChipsClick

/**
 * Created by NedaluOf on 8/11/2021.
 */
class InspirationalQuotesPagedAdapter(
    val onAuthorNameClick: (String) -> Unit,
    val onTagNameClick: (String) -> Unit,
) : PagingDataAdapter<QuoteModel, InspirationalQuotesPagedAdapter.InspirationalQuoteVH>(
    InspirationalQuoteDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InspirationalQuoteVH(
        ItemInspirationalQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: InspirationalQuoteVH, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class InspirationalQuoteVH(
        private val binding: ItemInspirationalQuoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quoteObj: QuoteModel) {
            binding.run {
                callback = object : ChipsClick {
                    override fun onChipClick(tag: String) {
                        onTagNameClick(tag)
                    }
                }
                quote = quoteObj
                executePendingBindings()
                author.run {
                    paintFlags = Paint.UNDERLINE_TEXT_FLAG;
                    setOnClickListener { onAuthorNameClick(quoteObj.authorSlug) }
                }
            }
        }
    }

    private class InspirationalQuoteDiffCallback : DiffUtil.ItemCallback<QuoteModel>() {
        override fun areItemsTheSame(oldItem: QuoteModel, newItem: QuoteModel) =
            oldItem.content == newItem.content

        override fun areContentsTheSame(oldItem: QuoteModel, newItem: QuoteModel) =
            oldItem == newItem
    }
}