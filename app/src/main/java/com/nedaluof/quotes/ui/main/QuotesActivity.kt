package com.nedaluof.quotes.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.PagerSnapHelper
import com.nedaluof.domain.model.quote.QuoteModel
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.ActivityQuotesBinding
import com.nedaluof.quotes.ui.base.BaseActivity
import com.nedaluof.quotes.ui.base.LoadStateFooterAdapter
import com.nedaluof.quotes.ui.main.adapters.QuotesPagedAdapter
import com.nedaluof.quotes.ui.main.adapters.TagsAdapter
import com.nedaluof.quotes.ui.main.authorquotes.AuthorQuotesSheet
import com.nedaluof.quotes.ui.main.quoteviewer.QuoteViewerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuotesActivity : BaseActivity<ActivityQuotesBinding>() {

  override val bindingVariable = 0
  override val layoutId = R.layout.activity_quotes
  private val quotesViewModel by viewModels<QuotesViewModel>()
  override fun getViewModel() = quotesViewModel

  private var tagsAdapter: TagsAdapter? = null
  private var quotesPagedAdapter: QuotesPagedAdapter? = null
  private var quotesJob: Job? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initTagsRecyclerView()
    initQuotesRecyclerView()
    observeViewModel()
  }

  private fun initTagsRecyclerView() {
    tagsAdapter = TagsAdapter { tagName, position ->
      loadQuotes(tagName)
      viewBinding.tagsRecyclerView.scrollToPosition(position)
    }

    with(viewBinding.tagsRecyclerView) {
      adapter = tagsAdapter
      PagerSnapHelper().attachToRecyclerView(this)
    }
  }

  private fun initQuotesRecyclerView() {
    quotesPagedAdapter =
      QuotesPagedAdapter(::openQuoteViewer, ::openAuthorQuotes).apply {
        with(viewBinding) {
          quotesRecyclerView.adapter = withLoadStateFooter(
            footer = LoadStateFooterAdapter { this@apply.retry() }
          )
          addLoadStateListener { loadState ->
            message.isVisible = false
            if (loadState.refresh is LoadState.Loading) {
              //startShimmer()
              quotesProgress.isVisible = true
            } else {
              //stopShimmer()
              quotesProgress.isVisible = false
              message.isVisible = snapshot().isEmpty()
              val error = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
              }
              error?.let {
                if (snapshot().isEmpty()) {
                  message.isVisible = true
                  message.text = error.error.message.toString()
                }
              }
            }
          }
        }
      }
  }

  private fun observeViewModel() {
    lifecycleScope.launch {
      quotesViewModel.tagsList.collectLatest(tagsAdapter!!::addItems)
    }
    loadQuotes("all")
  }

  private fun loadQuotes(tagName: String) {
    quotesJob?.cancel()
    quotesJob = lifecycleScope.launch {
      quotesViewModel.loadQuotes(tagName).collectLatest { data ->
        quotesPagedAdapter?.refresh()
        quotesPagedAdapter?.submitData(data)
      }
    }
  }

  private fun openAuthorQuotes(authorSlug: String) {
    AuthorQuotesSheet.buildInstance(authorSlug)
      .show(supportFragmentManager)
  }

  private fun openQuoteViewer(quoteModel: QuoteModel) {
    startActivity(QuoteViewerActivity.getIntent(this, quoteModel))
  }

  override fun onDestroy() {
    super.onDestroy()
    quotesPagedAdapter = null
    if (quotesJob?.isActive!!) {
      quotesJob?.cancel()
    }
    quotesJob = null
  }
}