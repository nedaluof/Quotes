package com.nedaluof.domain.usecases.quotesbyauthor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nedaluof.data.repository.quotes.QuotesRepository
import com.nedaluof.domain.model.quote.QuoteModel
import com.nedaluof.domain.model.quote.QuoteModelMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by NedaluOf on 8/11/2021.
 */
class QuotesByAuthorUseCaseImpl @Inject constructor(
    private val repository: QuotesRepository,
    private val mapper: QuoteModelMapper
) : QuotesByAuthorUseCase {

    override fun loadQuotesByAuthorPaginated(authorSlug: String): Flow<PagingData<QuoteModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                QuotesByAuthorPagingSource(
                    authorSlug,
                    repository,
                    mapper
                )
            }
        ).flow
    }
}