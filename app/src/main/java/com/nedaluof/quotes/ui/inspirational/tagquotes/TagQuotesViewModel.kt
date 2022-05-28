package com.nedaluof.quotes.ui.inspirational.tagquotes

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nedaluof.domain.usecases.quotesbytag.QuotesByTagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by NedaluOf on 8/10/2021.
 */
@HiltViewModel
class TagQuotesViewModel @Inject constructor(
    private val useCase: QuotesByTagUseCase
) : BaseViewModel() {
    fun loadInspirationalTagQuotes(tag:String) = useCase.loadQuotesByTagPaginated(tag)
        .cachedIn(viewModelScope)
}