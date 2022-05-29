package com.nedaluof.quotes.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nedaluof.domain.model.tag.TagModel
import com.nedaluof.domain.model.util.Result
import com.nedaluof.domain.usecases.quotes.QuotesUseCase
import com.nedaluof.domain.usecases.quotesbytag.QuotesByTagUseCase
import com.nedaluof.domain.usecases.tags.TagsUseCase
import com.nedaluof.quotes.util.bindingadapter.ChipsClick
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : ViewModel(){

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

  val tagsList = MutableStateFlow<List<TagModel>>(emptyList())

  private fun loadTags() {
    tagsUseCase.loadAllTags(
      viewModelScope
    ) { result ->
      when (result) {
        is Result.Loading -> _loading.value = result.loading
        is Result.Error -> Timber.e(result.error ?: "Error occur in loadTags")
        is Result.Success -> tagsList.value = result.data ?: emptyList()
      }
    }
  }

  fun loadAllQuotes() = quotesUseCase.loadQuotesPaginated()
    .cachedIn(viewModelScope)

  fun loadQuotesByTag(tag: String) = quotesByTagUseCase.loadQuotesByTagPaginated(tag)
    .cachedIn(viewModelScope)

  init {
    loadTags()
  }
}