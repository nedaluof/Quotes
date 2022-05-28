package com.nedaluof.data.datasource.remote.apiresponse

import com.google.gson.annotations.SerializedName

/**
 * Created by NedaluOf on 8/13/2021.
 */
data class GetQuotesResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("lastItemIndex") val lastItemIndex: Int?,
    @SerializedName("results") val results: List<Quote>
)

data class Quote (
    @SerializedName("tags") val tags : List<String> = emptyList(),
    @SerializedName("_id") val _id : String = "",
    @SerializedName("author") val author : String= "",
    @SerializedName("content") val content : String= "",
    @SerializedName("authorSlug") val authorSlug : String= "",
    @SerializedName("length") val length : Int = 0,
    @SerializedName("dateAdded") val dateAdded : String= "",
    @SerializedName("dateModified") val dateModified : String= ""
)
