package com.nedaluof.quotes.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.chip.Chip
import com.nedaluof.domain.model.tag.TagModel
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.ActivityMainBinding
import com.nedaluof.quotes.ui.base.BaseActivity
import com.nedaluof.quotes.ui.base.LoadStateFooterAdapter
import com.nedaluof.quotes.ui.main.authorquotes.AuthorQuotesSheet
import com.nedaluof.quotes.util.bindingadapter.ViewsBindingAdapters.generateChips
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuotesActivity : BaseActivity<ActivityMainBinding>() {

  override val bindingVariable = 0
  override val layoutId = R.layout.activity_main
  private val quotesViewModel by viewModels<QuotesViewModel>()
  override fun getViewModel() = quotesViewModel

  private var quotesPagedAdapter: QuotesPagedAdapter? = null
  private var quotesJob: Job? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initRecyclerView()
    observeViewModel()
  }

  private fun initRecyclerView() {
    quotesPagedAdapter =
      QuotesPagedAdapter { authorSlug -> openAuthorQuotes(authorSlug) }

    quotesPagedAdapter?.apply {
      viewBinding.recyclerView.adapter = withLoadStateFooter(
        footer = LoadStateFooterAdapter { this.retry() }
      )
      addLoadStateListener { loadState ->
        viewBinding.message.isVisible = false
        if (loadState.refresh is LoadState.Loading) {
          //startShimmer()
          viewBinding.progress.visibility = View.VISIBLE
        } else {
          //stopShimmer()
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

  private fun observeViewModel() {
    with(quotesViewModel) {

      lifecycleScope.launch {
        tagsList.collectLatest {
            initTagsView(it)
        }
      }

      quotesJob?.cancel()
      quotesJob = lifecycleScope.launch {
        loadAllQuotes().collectLatest { data ->
          quotesPagedAdapter?.submitData(data)
        }
      }
    }
  }

  private fun initTagsView(tags: List<TagModel>) {
    val list = tags.map { it.name }
    for (index in list.indices) {
      val tagName = tags[index].name
      val chip = Chip(this)
      /* val paddingDp = TypedValue.applyDimension(
           TypedValue.COMPLEX_UNIT_DIP, 10f,
           context.resources.displayMetrics
       ).toInt()
       chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)*/
      chip.text = tagName
      //chip.setCloseIconResource(R.drawable.ic_action_navigation_close);
      //chip.setCloseIconVisible(true);
      //Added click listener on close icon to remove tag from ChipGroup
      chip.setOnClickListener {
        Toast.makeText(this@QuotesActivity, tagName, Toast.LENGTH_SHORT).show()
      }
      /* chip.setOnCloseIconClickListener { v: View ->
           tags.remove(tagName)
           chipGroup.removeView(chip)
       }*/
      viewBinding.tagGroup.addView(chip)
    }
  }

  private fun openAuthorQuotes(authorSlug: String) {
    AuthorQuotesSheet.buildInstance(authorSlug)
      .show(supportFragmentManager)
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