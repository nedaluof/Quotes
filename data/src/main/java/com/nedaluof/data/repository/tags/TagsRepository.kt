package com.nedaluof.data.repository.tags

import com.nedaluof.data.datasource.remote.apiresponse.Tag
import retrofit2.Response

/**
 * Created by NedaluOf on 5/29/2022.
 */
interface TagsRepository {
  suspend fun getAllTags(): Response<List<Tag>>
}