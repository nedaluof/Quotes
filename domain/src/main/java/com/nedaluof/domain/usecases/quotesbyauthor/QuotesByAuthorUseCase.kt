package com.nedaluof.domain.usecases.quotesbyauthor

import androidx.paging.PagingData
import com.nedaluof.domain.model.quote.QuoteModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by NedaluOf on 8/13/2021.
 */
interface QuotesByAuthorUseCase {
    fun loadQuotesByAuthorPaginated(
        authorSlug: String
    ): Flow<PagingData<QuoteModel>>
}