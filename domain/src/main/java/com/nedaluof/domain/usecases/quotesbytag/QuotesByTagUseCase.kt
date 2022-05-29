package com.nedaluof.domain.usecases.quotesbytag

import androidx.paging.PagingData
import com.nedaluof.domain.model.quote.QuoteModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by NedaluOf on 8/14/2021.
 */
interface QuotesByTagUseCase {
    fun loadQuotesByTagPaginated(
        tag: String
    ): Flow<PagingData<QuoteModel>>
}