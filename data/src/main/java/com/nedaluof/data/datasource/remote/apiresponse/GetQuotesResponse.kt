package com.nedaluof.data.datasource.remote.apiresponse

import com.squareup.moshi.JsonClass

/**
 * Created by NedaluOf on 8/13/2021.
 */
@JsonClass(generateAdapter = true)
data class GetQuotesResponse(
  val count: Int,
  val totalCount: Int,
  val page: Int,
  val totalPages: Int,
  val lastItemIndex: Int?,
  val results: List<Quote>
)

@JsonClass(generateAdapter = true)
data class Quote(
  val tags: List<String> = emptyList(),
  val _id: String = "",
  val author: String = "",
  val content: String = "",
  val authorSlug: String = "",
  val length: Int = 0,
  val dateAdded: String = "",
  val dateModified: String = ""
)
