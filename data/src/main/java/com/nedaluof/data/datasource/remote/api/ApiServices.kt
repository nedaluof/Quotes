package com.nedaluof.data.datasource.remote.api

import com.nedaluof.data.datasource.remote.apiresponse.GetQuotesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by NedaluOf on 8/13/2021.
 */
interface ApiServices {

    //load all inspirational quotes
    @GET("quotes?limit=10")
    suspend fun getAllQuotes(
        @Query("page") page: Int = 1,
    ): Response<GetQuotesResponse>

    //load all author quotes
    @GET("quotes?limit=10")
    suspend fun getQuotesByAuthor(
        @Query("author") authorSlug: String = "",
        @Query("page") page: Int = 1,
    ): Response<GetQuotesResponse>

    //load all tag quotes
    @GET("quotes?limit=10")
    suspend fun getQuotesByTag(
        @Query("tags") tag: String = "",
        @Query("page") page: Int = 1,
    ): Response<GetQuotesResponse>

}