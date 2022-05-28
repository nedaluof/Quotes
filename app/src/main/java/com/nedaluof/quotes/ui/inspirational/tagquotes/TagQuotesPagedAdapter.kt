package com.nedaluof.quotes.ui.inspirational.tagquotes

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.domain.model.QuoteModel
import com.nedaluof.quotes.databinding.ItemInspirationalQuoteBinding

/**
 * Created by NedaluOf on 8/11/2021.
 */
class TagQuotesPagedAdapter(
    val onAuthorNameClick: (String) -> Unit
) : PagingDataAdapter<QuoteModel, TagQuotesPagedAdapter.TagQuoteVH>(
    InspirationalQuoteDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TagQuoteVH(
            ItemInspirationalQuoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: TagQuoteVH, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class TagQuoteVH(
        private val binding: ItemInspirationalQuoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(quoteObj: QuoteModel) {
            binding.run {
                //remove the tags chips group
                (tagGroup.parent as ViewGroup).removeView(tagGroup)
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