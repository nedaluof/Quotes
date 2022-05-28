package com.nedaluof.data.repository.quotes

import com.nedaluof.data.datasource.remote.apiresponse.GetQuotesResponse
import retrofit2.Response

/**
 * Created by NedaluOf on 5/28/2022.
 */
interface QuotesRepository {

    suspend fun getAllQuotes(
        page: Int
    ): Response<GetQuotesResponse>

    //load all author quotes
    suspend fun getQuotesByAuthor(
        authorSlug: String,
        page: Int,
    ): Response<GetQuotesResponse>

    //load all tag quotes
    suspend fun getQuotesByTag(
        tag: String = "",
        page: Int = 1,
    ): Response<GetQuotesResponse>
}