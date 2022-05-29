package com.nedaluof.quotes.ui.main.authorquotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nedaluof.domain.usecases.quotesbyauthor.QuotesByAuthorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by NedaluOf on 8/10/2021.
 */
@HiltViewModel
class AuthorQuotesViewModel @Inject constructor(
  private val useCase: QuotesByAuthorUseCase
) : ViewModel() {

  fun loadInspirationalAuthorQuotes(
    authorSlug: String
  ) = useCase.loadQuotesByAuthorPaginated(authorSlug)
    .cachedIn(viewModelScope)

}