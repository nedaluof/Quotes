package com.nedaluof.domain.usecases.tags

import com.nedaluof.data.datasource.remote.apiresponse.Tag
import com.nedaluof.data.repository.tags.TagsRepository
import com.nedaluof.domain.model.mapper.Mapper
import com.nedaluof.domain.model.tag.TagModel
import com.nedaluof.domain.model.util.Result
import com.nedaluof.domain.usecases.base.BaseUseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Created by NedaluOf on 5/29/2022.
 */
class TagsUseCaseImpl @Inject constructor(
  private val repository: TagsRepository,
  private val mapper: Mapper<Tag, TagModel>
) : TagsUseCase, BaseUseCase() {

  override fun loadAllTags(
    scope: CoroutineScope,
    result: (Result<List<TagModel>>) -> Unit
  ) {
    invoke(
      scope,
      apiToCall = { repository.getAllTags() },
      onLoading = { result(Result.Loading(it)) },
      onSuccess = { responseData ->
        val originalTagsList = mapper.fromList(responseData)
        val newTagsList = originalTagsList.toMutableList().also {
          it.add(0, TagModel("all", isSelected = true))
        }
        result(Result.Success(newTagsList))
      },
      onError = { result(Result.Error(it.getErrorMessage())) }
    )
  }
}