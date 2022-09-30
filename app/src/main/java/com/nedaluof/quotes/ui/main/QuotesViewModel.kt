package com.nedaluof.quotes.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nedaluof.domain.model.tag.TagModel
import com.nedaluof.domain.model.util.Result
import com.nedaluof.domain.usecases.quotes.QuotesUseCase
import com.nedaluof.domain.usecases.quotesbytag.QuotesByTagUseCase
import com.nedaluof.domain.usecases.tags.TagsUseCase
import com.nedaluof.quotes.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by NedaluOf on 5/28/2022.
 */
@HiltViewModel
class QuotesViewModel @Inject constructor(
  private val tagsUseCase: TagsUseCase,
  private val quotesUseCase: QuotesUseCase,
  private val quotesByTagUseCase: QuotesByTagUseCase
) : BaseViewModel() {

  val tagsProgress = MutableStateFlow(false)
  val tagsList = MutableStateFlow<List<TagModel>>(emptyList())

  private fun loadTags() {
    tagsUseCase.loadAllTags(
      viewModelScope
    ) { result ->
      when (result) {
        is Result.Loading -> tagsProgress.value = result.loading
        is Result.Error -> Timber.e(result.error ?: "Error occur in loadTags")
        is Result.Success -> tagsList.value = result.data ?: emptyList()
      }
    }
  }

  fun loadQuotes(tagName: String = "all") = if (tagName == "all") {
    loadAllQuotes()
  } else {
    loadQuotesByTag(tagName)
  }

  private fun loadAllQuotes() = quotesUseCase.loadQuotesPaginated()
    .cachedIn(viewModelScope)

  private fun loadQuotesByTag(tag: String) = quotesByTagUseCase.loadQuotesByTagPaginated(tag)
    .cachedIn(viewModelScope)

  init {
    loadTags()
  }
}