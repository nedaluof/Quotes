package com.nedaluof.quotes.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.quotes.databinding.ItemLoadStateBinding

/**
 * Created by NedaluOf on 8/11/2021.
 */
class LoadStateFooterAdapter(
  private val retry: () -> Unit,
) : LoadStateAdapter<LoadStateFooterAdapter.LoadStateVH>() {
  override fun onBindViewHolder(holder: LoadStateVH, loadState: LoadState) {
    holder.bind(loadState)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    loadState: LoadState,
  ) = LoadStateVH.create(parent, retry)


  class LoadStateVH(
    private val binding: ItemLoadStateBinding,
    retry: () -> Unit,
  ) : RecyclerView.ViewHolder(binding.root) {
    init {
      binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
      if (loadState is LoadState.Error) {
        binding.errorMsg.text = loadState.error.localizedMessage
      }

      binding.run {
        progressBar.isVisible = loadState is LoadState.Loading
        retryButton.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = loadState is LoadState.Error
      }
    }

    companion object {
      fun create(parent: ViewGroup, retry: () -> Unit): LoadStateVH {
        return LoadStateVH(
          ItemLoadStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
          ), retry
        )
      }
    }
  }
}