package com.nedaluof.quotes.ui.main.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.domain.model.quote.QuoteModel
import com.nedaluof.quotes.databinding.ItemQuoteBinding
import com.nedaluof.quotes.util.click

/**
 * Created by NedaluOf on 8/11/2021.
 */
class QuotesPagedAdapter(
  val onQuoteClicked: (QuoteModel) -> Unit,
  val onAuthorNameClicked: (String) -> Unit,
) : PagingDataAdapter<QuoteModel, QuotesPagedAdapter.QuoteVH>(
  InspirationalQuoteDiffCallback()
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = QuoteVH(
    ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
  )


  override fun onBindViewHolder(holder: QuoteVH, position: Int) {
    holder.bind(getItem(position)!!)
  }

  inner class QuoteVH(
    private val binding: ItemQuoteBinding
  ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(quoteModel: QuoteModel) {
      binding.run {
        quote = quoteModel
        executePendingBindings()
        root.click { onQuoteClicked(quoteModel) }
        author.run {
          paintFlags = Paint.UNDERLINE_TEXT_FLAG;
          click { onAuthorNameClicked(quoteModel.authorSlug) }
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