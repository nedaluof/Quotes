package com.nedaluof.data.repository.tags

import com.nedaluof.data.datasource.remote.api.ApiServices
import com.nedaluof.data.datasource.remote.apiresponse.Tag
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by NedaluOf on 5/29/2022.
 */
class TagsRepositoryImpl @Inject constructor(
  private val api: ApiServices
) : TagsRepository {

  override suspend fun getAllTags(): Response<List<Tag>> {
    return api.getAllTags()
  }
}