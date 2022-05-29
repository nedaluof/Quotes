package com.nedaluof.domain.usecases.tags

import com.nedaluof.domain.model.tag.TagModel
import com.nedaluof.domain.model.util.Result
import kotlinx.coroutines.CoroutineScope

/**
 * Created by NedaluOf on 5/29/2022.
 */
interface TagsUseCase {
  fun loadAllTags(
    scope: CoroutineScope,
    result: (Result<List<TagModel>>) -> Unit
  )
}