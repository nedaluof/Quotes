package com.nedaluof.domain.model.tag

import com.nedaluof.data.datasource.remote.apiresponse.Tag
import com.nedaluof.domain.model.mapper.Mapper
import javax.inject.Inject

/**
 * Created by NedaluOf on 5/29/2022.
 */
class TagModelMapper @Inject constructor() : Mapper<Tag, TagModel> {

  override fun from(inputModel: Tag) = TagModel(
    inputModel.name,
    try {
      inputModel.quoteCount.toString()
    } catch (e: Exception) {
      "0"
    }
  )

  override fun to(outputModel: TagModel) = Tag()
}