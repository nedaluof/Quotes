package com.nedaluof.data.datasource.remote.apiresponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by NedaluOf on 5/29/2022.
 */
@JsonClass(generateAdapter = true)
data class Tag(
  @Json(name = "_id") val id: String = "",
  val name: String = "",
  val dateAdded: String = "",
  val dateModified: String = "",
  @Json(name = "__v") val v: Int = 0,
  val quoteCount: Int = 0
)
