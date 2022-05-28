package com.nedaluof.data.repository.quotes

import com.nedaluof.data.datasource.remote.api.ApiServices
import com.nedaluof.data.datasource.remote.apiresponse.GetQuotesResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by NedaluOf on 5/28/2022.
 */
class QuotesRepositoryImpl @Inject constructor(
    private val api: ApiServices
) : QuotesRepository {

    override suspend fun getAllQuotes(page: Int): Response<GetQuotesResponse> {
        return api.getAllQuotes(page)
    }

    override suspend fun getQuotesByAuthor(
        authorSlug: String,
        page: Int
    ): Response<GetQuotesResponse> {
        return api.getQuotesByAuthor(authorSlug, page)
    }

    override suspend fun getQuotesByTag(tag: String, page: Int): Response<GetQuotesResponse> {
        return api.getQuotesByTag(tag, page)
    }
}