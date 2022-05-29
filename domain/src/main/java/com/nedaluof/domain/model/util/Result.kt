package com.nedaluof.domain.model.util

/**
 * Created by nedaluof on 12/11/2020.
 * Updated by NedaluOf on 5/29/2022.
 */
sealed class Result<out T>(
  val data: T? = null,
  val error: String? = null,
  val loading: Boolean = false
) {
  class Loading<T>(loading: Boolean = false) : Result<T>(loading = loading)
  class Success<T>(data: T? = null) : Result<T>(data)
  class Error<T>(error: String? = null) : Result<T>(error = error)
}