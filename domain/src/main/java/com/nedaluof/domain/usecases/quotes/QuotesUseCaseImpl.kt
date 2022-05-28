package com.nedaluof.domain.usecases.quotes

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nedaluof.data.repository.quotes.QuotesRepository
import com.nedaluof.domain.model.QuoteModel
import com.nedaluof.domain.model.QuoteModelMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by NedaluOf on 8/11/2021.
 */
class QuotesUseCaseImpl @Inject constructor(
    private val repository: QuotesRepository,
    private val mapper: QuoteModelMapper
) : QuotesUseCase {

    override fun loadQuotesPaginated(): Flow<PagingData<QuoteModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                QuotesPagingSource(
                    repository,
                    mapper
                )
            }
        ).flow
    }
}