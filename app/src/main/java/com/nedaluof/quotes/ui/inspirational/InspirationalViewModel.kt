package com.nedaluof.quotes.ui.inspirational

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nedaluof.domain.usecases.quotes.QuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by NedaluOf on 8/10/2021.
 */
@HiltViewModel
class InspirationalViewModel @Inject constructor(
    private val useCase: QuotesUseCase
) : BaseViewModel() {
    fun loadInspirationalQuotes() = useCase.loadInspirationalQuotesPaginated()
        .cachedIn(viewModelScope)
}