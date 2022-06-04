package com.nedaluof.quotes.ui.main.authorquotes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.SheetAuthorQuotesBinding
import com.nedaluof.quotes.ui.base.BaseBottomSheet
import com.nedaluof.quotes.ui.base.LoadStateFooterAdapter
import com.nedaluof.quotes.util.click
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by NedaluOf on 8/10/2021.
 */
@AndroidEntryPoint
class AuthorQuotesSheet : BaseBottomSheet<SheetAuthorQuotesBinding>() {

  override var layoutId = R.layout.sheet_author_quotes
  override var bindingVariable = 0
  private val authorViewModel by viewModels<AuthorQuotesViewModel>()
  override fun getViewModel() = authorViewModel

  private var quotesPagedAdapter: AuthorQuotesPagedAdapter? = null
  private var quotesJob: Job? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initBottomSheetBehavior { state ->
      val visibility = when (state) {
        BottomSheetBehavior.STATE_EXPANDED -> true
        else -> false
      }
      with(viewBinding) {
        sheetBar.isVisible = visibility
        authorName.isVisible = !visibility
      }
    }
    initRecyclerView()
    loadComingArguments()
    initClicks()
  }

  private fun initRecyclerView() {
    quotesPagedAdapter = AuthorQuotesPagedAdapter().apply {
      viewBinding.recyclerView.adapter = withLoadStateFooter(
        footer = LoadStateFooterAdapter { this.retry() }
      )
      addLoadStateListener { loadState ->
        viewBinding.message.isVisible = false
        if (loadState.refresh is LoadState.Loading) {
          viewBinding.progress.visibility = View.VISIBLE
        } else {
          viewBinding.progress.visibility = View.GONE
          viewBinding.message.isVisible = snapshot().isEmpty()
          val error = when {
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
          }
          error?.let {
            if (snapshot().isEmpty()) {
              viewBinding.message.isVisible = true
              viewBinding.message.text = error.error.message.toString()
            }
          }
        }
      }
    }
  }

  private fun loadComingArguments() {
    arguments?.getString(AUTHOR_SLUG_KEY)?.let { comingAuthorName ->
      with(viewBinding) {
        authorName.text = comingAuthorName
        authorNameSecond.text = comingAuthorName
      }
      loadAuthorQuotes(comingAuthorName)
    }
  }

  private fun initClicks() {
    viewBinding.closeBtn.click(::dismiss)
  }

  private fun loadAuthorQuotes(authorSlug: String) {
    quotesJob?.cancel()
    quotesJob = lifecycleScope.launch {
      authorViewModel.loadInspirationalAuthorQuotes(authorSlug).collectLatest { data ->
        quotesPagedAdapter?.submitData(data)
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    quotesPagedAdapter = null
    if (quotesJob?.isActive!!) {
      quotesJob?.cancel()
    }
    quotesJob = null
  }

  companion object {
    private const val AUTHOR_SLUG_KEY = "AUTHOR_SLUG_KEY"

    fun buildInstance(
      authorSlug: String
    ) = AuthorQuotesSheet().apply {
      arguments = Bundle().also {
        it.putString(AUTHOR_SLUG_KEY, authorSlug)
      }
    }
  }

}