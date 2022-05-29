package com.nedaluof.domain.usecases.quotes

import androidx.paging.PagingData
import com.nedaluof.domain.model.quote.QuoteModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by NedaluOf on 8/13/2021.
 */
interface QuotesUseCase {
    fun loadQuotesPaginated(): Flow<PagingData<QuoteModel>>
}