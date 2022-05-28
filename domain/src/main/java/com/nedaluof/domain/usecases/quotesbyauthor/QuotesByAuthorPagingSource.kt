package com.nedaluof.domain.usecases.quotesbyauthor

import com.nedaluof.data.datasource.remote.apiresponse.GetQuotesResponse
import com.nedaluof.data.repository.quotes.QuotesRepository
import com.nedaluof.domain.model.QuoteModel
import com.nedaluof.domain.model.QuoteModelMapper
import com.nedaluof.domain.usecases.base.BasePagingSource
import retrofit2.Response

/**
 * Created by NedaluOf on 8/14/2021.
 */
class QuotesByAuthorPagingSource(
    private val authorSlug: String,
    private val repository: QuotesRepository,
    private val mapper: QuoteModelMapper
) : BasePagingSource<QuoteModel, GetQuotesResponse>() {

    override suspend fun apiToCall(page: Int): Response<GetQuotesResponse> {
        return repository.getQuotesByAuthor(authorSlug, page)
    }

    override fun mapData(body: GetQuotesResponse?): List<QuoteModel> {
        return mapper.fromList(body?.results!!)
    }
}